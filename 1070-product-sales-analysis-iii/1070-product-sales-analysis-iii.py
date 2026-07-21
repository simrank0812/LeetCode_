import pandas as pd

def sales_analysis(sales: pd.DataFrame) -> pd.DataFrame:
    first_year = sales.groupby("product_id")["year"].transform("min")
    result = sales[sales["year"] == first_year]

    return result[["product_id", "year", "quantity", "price"]].rename(
        columns={"year": "first_year"}
    )