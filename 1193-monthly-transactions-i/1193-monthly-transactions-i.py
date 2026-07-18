import pandas as pd

def monthly_transactions(transactions: pd.DataFrame) -> pd.DataFrame:
    transactions = transactions.copy()

    transactions["month"] = transactions["trans_date"].dt.strftime("%Y-%m")
    transactions["approved"] = (transactions["state"] == "approved").astype(int)
    transactions["approved_amount"] = transactions["amount"].where(
        transactions["state"] == "approved", 0
    )

    return (
        transactions.groupby(["month", "country"], dropna=False, as_index=False)
        .agg(
            trans_count=("id", "count"),
            approved_count=("approved", "sum"),
            trans_total_amount=("amount", "sum"),
            approved_total_amount=("approved_amount", "sum"),
        )
    )