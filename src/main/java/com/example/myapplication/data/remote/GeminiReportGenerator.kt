package com.example.myapplication.data.remote

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.example.myapplication.data.model.Asset
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GeminiReportGenerator(private val apiKey: String) {

    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-pro",
        apiKey = apiKey
    )

    suspend fun generateAuditReport(assets: List<Asset>): String = withContext(Dispatchers.IO) {
        val assetListString = assets.joinToString("\n") { 
            "- ${it.name} (${it.category}) at ${it.location}: Condition ${it.conditionStatus}, Quantity ${it.quantity}" 
        }

        val prompt = """
            You are a professional Digital Asset Auditor for government schools.
            Based on the following asset data, generate a comprehensive audit report for the school year 2025-2026.
            
            Assets:
            $assetListString
            
            The report should include:
            1. Executive Summary
            2. Asset Distribution Analysis
            3. Condition Assessment Summary (Highlight any RED items)
            4. Recommendations for Maintenance and Procurement
            
            Format the report professionally.
        """.trimIndent()

        try {
            val response = generativeModel.generateContent(prompt)
            response.text ?: "Error: No response from Gemini"
        } catch (e: Exception) {
            "Error generating report: ${e.message}"
        }
    }
}
