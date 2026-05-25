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
                    <title>Welcome to my project</title>
                    <style>
                        body {
                            font-family: Arial, Helvetica, sans-serif;
                            margin: 0;
                            background: #f5f6f8;
                            color: #1f2937;
                            min-height: 100vh;
                            display: flex;
                            align-items: center;
                            justify-content: center;
                            padding: 24px;
                        }
                        .container {
                            background: #ffffff;
                            max-width: 760px;
                            width: 100%;
                            padding: 40px;
                            border: 1px solid #e5e7eb;
                            border-radius: 12px;
                            box-shadow: 0 8px 24px rgba(15, 23, 42, 0.08);
                        }
                        h1 {
                            font-size: 36px;
                            margin: 0 0 12px;
                            font-weight: 700;
                        }
                        p {
                            line-height: 1.7;
                            font-size: 16px;
                            margin: 0 0 16px;
                        }
                        .note {
                            color: #4b5563;
                            margin-bottom: 28px;
                        }
                        .links {
                            display: flex;
                            gap: 12px;
                            flex-wrap: wrap;
                            margin: 24px 0 32px;
                        }
                        .btn {
                            padding: 12px 18px;
                            border-radius: 8px;
                            border: 1px solid #d1d5db;
                            text-decoration: none;
                            display: inline-block;
                            color: #111827;
                            background: #f9fafb;
                        }
                        .btn-primary {
                            background: #111827;
                            color: #ffffff;
                            border-color: #111827;
                        }
                        .section-title {
                            font-size: 18px;
                            margin: 0 0 12px;
                        }
                        .info {
                            background: #f9fafb;
                            padding: 16px;
                            border-radius: 8px;
                            border: 1px solid #e5e7eb;
                            margin-bottom: 24px;
                        }
                        ul {
                            margin: 0;
                            padding-left: 20px;
                        }
                        li {
                            margin-bottom: 8px;
                        }
                        @media (max-width: 640px) {
                            .container {
                                padding: 28px 20px;
                            }
                            h1 {
                                font-size: 28px;
                            }
                        }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <h1>Welcome to my project</h1>
                        <p class="note">This is the Personal Finance Manager API. It helps track income, expenses, categories, goals, and reports.</p>

                        <div class="links">
                            <a href="/api/routes" class="btn btn-primary">View API routes</a>
                            <a href="/api/health" class="btn">Health check</a>
                        </div>

                        <div class="info">
                            <div class="section-title">What you can do</div>
                            <ul>
                                <li>Register and log in with session-based authentication</li>
                                <li>Create and manage transactions</li>
                                <li>Use categories and savings goals</li>
                                <li>View monthly and yearly reports</li>
                            </ul>
                        </div>
                    </div>
                </body>
                </html>
                """;
    }
}
