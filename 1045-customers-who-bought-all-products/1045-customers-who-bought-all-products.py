import pandas as pd

def find_customers(customer: pd.DataFrame, product: pd.DataFrame) -> pd.DataFrame:
    
    # Total number of distinct products
    total_products = product["product_key"].nunique()
    
    # Count distinct products bought by each customer
    result = (
        customer.groupby("customer_id")["product_key"]
        .nunique()
        .reset_index(name="cnt")
    )
    
    # Keep customers who bought all products
    result = result[result["cnt"] == total_products][["customer_id"]]
    
    return result