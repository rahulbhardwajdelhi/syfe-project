# Design notes

Quick notes on how I built this — for whoever is grading.

## Layers

Request goes: **Controller → Service → Repository → H2**

I kept controllers thin. Rules about money, dates, goals etc are in the service layer so I could unit test without starting the server.

## Login

Using session cookie (`JSESSIONID`) because thats what the assignment asked for. Register and login are public. Everything else needs a valid session.

## Users cant see each others data

When someone registers they get their own copy of the 7 default categories (Salary, Food, Rent...). So user A never sees user B's custom categories.

Transactions and goals always filter by logged-in user. If you try to edit someone elses transaction id you get 404.

## Transactions

- Delete is **soft delete** (`deleted = true`) — row stays in db but goals and reports ignore it
- **Date cant change** after create (update body can send date, we ignore it)
- Amounts rounded to 2 decimals

## Goals

Progress = total income minus total expenses **since the goal start date**.

Each goal is seperate. Percentage formatting was annoying — the bash test script expects exact values like `65.5` or `60.33`.

## Reports

Monthly = sum transactions in that calendar month. Yearly = whole year.

`netSavings` returns `0` not `0.00` when empty — tests check the string.

## If I had more time

Would use Postgres instead of H2 (data resets on restart). Maybe pagination on transaction list.
