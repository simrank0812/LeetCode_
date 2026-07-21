import pandas as pd

def find_investments(insurance: pd.DataFrame) -> pd.DataFrame:
    # tiv_2015 values that appear more than once
    same_tiv = insurance.groupby("tiv_2015")["pid"].transform("count") > 1

    # (lat, lon) pairs that are unique
    unique_city = insurance.groupby(["lat", "lon"])["pid"].transform("count") == 1

    ans = insurance.loc[same_tiv & unique_city, "tiv_2016"].sum()

    return pd.DataFrame({
        "tiv_2016": [round(ans, 2)]
    })