package com.syfe.pfm.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiRoutesController {

    @GetMapping(value = "/api/routes", produces = MediaType.TEXT_HTML_VALUE)
    public String home() {
        return """
                <!doctype html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>API routes</title>
                    <style>
                        body {
                            font-family: Arial, Helvetica, sans-serif;
                            margin: 0;
                            padding: 24px;
                            background: #f5f6f8;
                            color: #1f2937;
                        }
                        .card {
                            max-width: 1040px;
                            margin: 0 auto;
                            background: white;
                            border-radius: 12px;
                            padding: 28px;
                            border: 1px solid #e5e7eb;
                            box-shadow: 0 8px 24px rgba(15, 23, 42, 0.08);
                        }
                        h1 {
                            margin: 0 0 12px;
                        }
                        code {
                            background: #eef2f7;
                            padding: 2px 8px;
                            border-radius: 6px;
                        }
                        table {
                            width: 100%;
                            border-collapse: collapse;
                            margin-top: 18px;
                        }
                        th, td {
                            border-bottom: 1px solid #e5e7eb;
                            text-align: left;
                            padding: 12px 10px;
                            vertical-align: top;
                        }
                        th {
                            background: #f9fafb;
                        }
                        .method {
                            font-weight: bold;
                        }
                        .meta {
                            color: #6b7280;
                        }
                    </style>
                </head>
                <body>
                    <div class="card">
                        <h1>API routes</h1>
                        <p class="meta">Base path: <code>/api</code></p>
                        <p><a href="/">Back to home</a></p>
                        <p>This page lists the available endpoints for the Personal Finance Manager project.</p>

                        <table>
                            <thead>
                                <tr>
                                    <th>Method</th>
                                    <th>Route</th>
                                    <th>Access</th>
                                    <th>Notes</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr><td class="method">GET</td><td><code>/api/</code></td><td>Public</td><td>Welcome page</td></tr>
                                <tr><td class="method">GET</td><td><code>/api/routes</code></td><td>Public</td><td>This page</td></tr>
                                <tr><td class="method">GET</td><td><code>/api/health</code></td><td>Public</td><td>Health check</td></tr>
                                <tr><td class="method">POST</td><td><code>/api/auth/register</code></td><td>Public</td><td>Create an account</td></tr>
                                <tr><td class="method">POST</td><td><code>/api/auth/login</code></td><td>Public</td><td>Log in and create a session</td></tr>
                                <tr><td class="method">POST</td><td><code>/api/auth/logout</code></td><td>Auth</td><td>Log out</td></tr>
                                <tr><td class="method">GET</td><td><code>/api/categories</code></td><td>Auth</td><td>List categories</td></tr>
                                <tr><td class="method">POST</td><td><code>/api/categories</code></td><td>Auth</td><td>Create a custom category</td></tr>
                                <tr><td class="method">DELETE</td><td><code>/api/categories/{name}</code></td><td>Auth</td><td>Delete a custom category</td></tr>
                                <tr><td class="method">GET</td><td><code>/api/transactions</code></td><td>Auth</td><td>Query filters: startDate, endDate, categoryId, category, type</td></tr>
                                <tr><td class="method">POST</td><td><code>/api/transactions</code></td><td>Auth</td><td>Create a transaction</td></tr>
                                <tr><td class="method">PUT</td><td><code>/api/transactions/{id}</code></td><td>Auth</td><td>Update amount, category, or description</td></tr>
                                <tr><td class="method">DELETE</td><td><code>/api/transactions/{id}</code></td><td>Auth</td><td>Delete a transaction</td></tr>
                                <tr><td class="method">GET</td><td><code>/api/goals</code></td><td>Auth</td><td>List goals</td></tr>
                                <tr><td class="method">POST</td><td><code>/api/goals</code></td><td>Auth</td><td>Create a goal</td></tr>
                                <tr><td class="method">GET</td><td><code>/api/goals/{id}</code></td><td>Auth</td><td>Get one goal</td></tr>
                                <tr><td class="method">PUT</td><td><code>/api/goals/{id}</code></td><td>Auth</td><td>Update target amount or date</td></tr>
                                <tr><td class="method">DELETE</td><td><code>/api/goals/{id}</code></td><td>Auth</td><td>Delete a goal</td></tr>
                                <tr><td class="method">GET</td><td><code>/api/reports/monthly/{year}/{month}</code></td><td>Auth</td><td>Monthly report</td></tr>
                                <tr><td class="method">GET</td><td><code>/api/reports/yearly/{year}</code></td><td>Auth</td><td>Yearly report</td></tr>
                            </tbody>
                        </table>
                    </div>
                </body>
                </html>
                """;
    }
}