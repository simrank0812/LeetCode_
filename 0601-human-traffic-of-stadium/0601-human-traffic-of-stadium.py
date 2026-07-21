import pandas as pd

def human_traffic(stadium: pd.DataFrame) -> pd.DataFrame:
    # Keep only rows with at least 100 people
    df = stadium[stadium["people"] >= 100].copy()

    # Consecutive ids have the same group value (id - index)
    df["grp"] = df["id"] - range(len(df))

    # Groups having at least 3 consecutive rows
    valid = df.groupby("grp").filter(lambda x: len(x) >= 3)

    return valid.drop(columns="grp").sort_values("visit_date")