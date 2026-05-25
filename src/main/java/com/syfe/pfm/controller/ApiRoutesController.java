package com.syfe.pfm.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiRoutesController {

    @GetMapping(value = "/routes", produces = MediaType.TEXT_HTML_VALUE)
    public String home() {
        return """
                <!doctype html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Personal Finance Manager API</title>
                    <style>
                        body {
                            font-family: Arial, sans-serif;
                            margin: 0;
                            padding: 40px;
                            background: #f7f9fc;
                            color: #1f2937;
                        }
                        .card {
                            max-width: 980px;
                            margin: 0 auto;
                            background: white;
                            border-radius: 16px;
                            padding: 32px;
                            box-shadow: 0 10px 30px rgba(15, 23, 42, 0.08);
                        }
                        h1 {
                            margin-top: 0;
                        }
                        code {
                            background: #eef2ff;
                            padding: 2px 6px;
                            border-radius: 6px;
                        }
                        table {
                            width: 100%;
                            border-collapse: collapse;
                            margin-top: 20px;
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
                        .get { color: #047857; }
                        .post { color: #b45309; }
                        .put { color: #1d4ed8; }
                        .delete { color: #b91c1c; }
                    </style>
                </head>
                <body>
                    <div class="card">
                        <h1>Personal Finance Manager API</h1>
                        <p>Base path: <code>/api</code></p>
                        <p><a href="/api/">Back to home</a></p>
                        <p>Use <code>POST /api/auth/register</code> and <code>POST /api/auth/login</code> first. All other routes require the session cookie.</p>

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
                                <tr><td class="method get">GET</td><td><code>/api/</code></td><td>Public</td><td>Landing page</td></tr>
                                <tr><td class="method get">GET</td><td><code>/api/routes</code></td><td>Public</td><td>Routes page</td></tr>
                                <tr><td class="method get">GET</td><td><code>/api/health</code></td><td>Public</td><td>Health check</td></tr>
                                <tr><td class="method post">POST</td><td><code>/api/auth/register</code></td><td>Public</td><td>Create account</td></tr>
                                <tr><td class="method post">POST</td><td><code>/api/auth/login</code></td><td>Public</td><td>Create session cookie</td></tr>
                                <tr><td class="method post">POST</td><td><code>/api/auth/logout</code></td><td>Auth</td><td>Invalidate session</td></tr>
                                <tr><td class="method get">GET</td><td><code>/api/categories</code></td><td>Auth</td><td>List categories</td></tr>
                                <tr><td class="method post">POST</td><td><code>/api/categories</code></td><td>Auth</td><td>Create custom category</td></tr>
                                <tr><td class="method delete">DELETE</td><td><code>/api/categories/{name}</code></td><td>Auth</td><td>Delete custom category</td></tr>
                                <tr><td class="method get">GET</td><td><code>/api/transactions</code></td><td>Auth</td><td>Filters: startDate, endDate, categoryId, category, type</td></tr>
                                <tr><td class="method post">POST</td><td><code>/api/transactions</code></td><td>Auth</td><td>Create transaction</td></tr>
                                <tr><td class="method put">PUT</td><td><code>/api/transactions/{id}</code></td><td>Auth</td><td>Update amount/category/description</td></tr>
                                <tr><td class="method delete">DELETE</td><td><code>/api/transactions/{id}</code></td><td>Auth</td><td>Soft delete transaction</td></tr>
                                <tr><td class="method get">GET</td><td><code>/api/goals</code></td><td>Auth</td><td>List goals</td></tr>
                                <tr><td class="method post">POST</td><td><code>/api/goals</code></td><td>Auth</td><td>Create goal</td></tr>
                                <tr><td class="method get">GET</td><td><code>/api/goals/{id}</code></td><td>Auth</td><td>Get one goal</td></tr>
                                <tr><td class="method put">PUT</td><td><code>/api/goals/{id}</code></td><td>Auth</td><td>Update target amount/date</td></tr>
                                <tr><td class="method delete">DELETE</td><td><code>/api/goals/{id}</code></td><td>Auth</td><td>Delete goal</td></tr>
                                <tr><td class="method get">GET</td><td><code>/api/reports/monthly/{year}/{month}</code></td><td>Auth</td><td>Monthly report</td></tr>
                                <tr><td class="method get">GET</td><td><code>/api/reports/yearly/{year}</code></td><td>Auth</td><td>Yearly report</td></tr>
                            </tbody>
                        </table>
                    </div>
                </body>
                </html>
                """;
    }
}