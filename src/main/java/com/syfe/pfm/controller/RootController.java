package com.syfe.pfm.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public String root() {
        return """
                <!doctype html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Personal Finance Manager</title>
                    <style>
                        * {
                            margin: 0;
                            padding: 0;
                            box-sizing: border-box;
                        }
                        body {
                            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', 'Roboto', 'Helvetica', 'Arial', sans-serif;
                            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                            min-height: 100vh;
                            display: flex;
                            align-items: center;
                            justify-content: center;
                            padding: 20px;
                        }
                        .container {
                            background: white;
                            border-radius: 20px;
                            box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
                            max-width: 600px;
                            padding: 60px 40px;
                            text-align: center;
                        }
                        h1 {
                            color: #1f2937;
                            margin-bottom: 15px;
                            font-size: 32px;
                        }
                        .subtitle {
                            color: #6b7280;
                            margin-bottom: 40px;
                            font-size: 16px;
                        }
                        .description {
                            color: #4b5563;
                            margin-bottom: 40px;
                            line-height: 1.6;
                        }
                        .button-group {
                            display: flex;
                            gap: 15px;
                            flex-direction: column;
                        }
                        .btn {
                            padding: 14px 28px;
                            border: none;
                            border-radius: 10px;
                            font-size: 16px;
                            font-weight: 600;
                            cursor: pointer;
                            transition: all 0.3s ease;
                            text-decoration: none;
                            display: inline-block;
                        }
                        .btn-primary {
                            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                            color: white;
                        }
                        .btn-primary:hover {
                            transform: translateY(-2px);
                            box-shadow: 0 10px 25px rgba(102, 126, 234, 0.4);
                        }
                        .btn-secondary {
                            background: #f3f4f6;
                            color: #1f2937;
                            border: 2px solid #e5e7eb;
                        }
                        .btn-secondary:hover {
                            background: #e5e7eb;
                        }
                        .code {
                            background: #f3f4f6;
                            padding: 12px 16px;
                            border-radius: 8px;
                            font-family: 'Courier New', monospace;
                            color: #d946ef;
                            font-size: 14px;
                            margin: 20px 0;
                            word-break: break-all;
                        }
                        .features {
                            margin-top: 50px;
                            text-align: left;
                            display: grid;
                            grid-template-columns: 1fr 1fr;
                            gap: 20px;
                        }
                        .feature {
                            padding: 20px;
                            background: #f9fafb;
                            border-radius: 12px;
                            border-left: 4px solid #667eea;
                        }
                        .feature-title {
                            font-weight: 600;
                            color: #1f2937;
                            margin-bottom: 8px;
                        }
                        .feature-text {
                            color: #6b7280;
                            font-size: 14px;
                        }
                        @media (max-width: 600px) {
                            .container {
                                padding: 40px 25px;
                            }
                            h1 {
                                font-size: 24px;
                            }
                            .features {
                                grid-template-columns: 1fr;
                            }
                        }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <h1>💰 Personal Finance Manager</h1>
                        <p class="subtitle">Track your income, expenses, and savings goals</p>
                        
                        <p class="description">
                            A comprehensive REST API for managing your personal finances with ease.
                        </p>

                        <div class="button-group">
                            <a href="/api/" class="btn btn-primary">📚 View All API Routes</a>
                            <a href="/api/health" class="btn btn-secondary">🏥 Health Check</a>
                        </div>

                        <div class="code">Base URL: /api</div>

                        <div class="features">
                            <div class="feature">
                                <div class="feature-title">🔐 Authentication</div>
                                <div class="feature-text">Session-based login with secure cookies</div>
                            </div>
                            <div class="feature">
                                <div class="feature-title">💳 Transactions</div>
                                <div class="feature-text">Track income and expenses</div>
                            </div>
                            <div class="feature">
                                <div class="feature-title">📂 Categories</div>
                                <div class="feature-text">Organize with custom categories</div>
                            </div>
                            <div class="feature">
                                <div class="feature-title">🎯 Goals</div>
                                <div class="feature-text">Set and track savings goals</div>
                            </div>
                            <div class="feature">
                                <div class="feature-title">📊 Reports</div>
                                <div class="feature-text">Monthly and yearly reports</div>
                            </div>
                            <div class="feature">
                                <div class="feature-title">🛡️ Secure</div>
                                <div class="feature-text">User data isolation & encryption</div>
                            </div>
                        </div>
                    </div>
                </body>
                </html>
                """;
    }
}
