import pandas as pd

def user_activity(activity: pd.DataFrame) -> pd.DataFrame:
    end_date = pd.Timestamp("2019-07-27")
    start_date = end_date - pd.Timedelta(days=29)

    filtered = activity[
        (activity["activity_date"] >= start_date) &
        (activity["activity_date"] <= end_date)
    ]

    return (
        filtered.groupby("activity_date")["user_id"]
        .nunique()
        .reset_index(name="active_users")
        .rename(columns={"activity_date": "day"})
    )