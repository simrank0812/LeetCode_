import pandas as pd

def trips_and_users(trips: pd.DataFrame, users: pd.DataFrame) -> pd.DataFrame:
    # Filter required dates
    trips = trips[
        trips["request_at"].between("2013-10-01", "2013-10-03")
    ].copy()

    # Add client banned status
    trips = trips.merge(
        users[["users_id", "banned"]],
        left_on="client_id",
        right_on="users_id",
        how="left"
    ).rename(columns={"banned": "client_banned"}).drop(columns="users_id")

    # Add driver banned status
    trips = trips.merge(
        users[["users_id", "banned"]],
        left_on="driver_id",
        right_on="users_id",
        how="left"
    ).rename(columns={"banned": "driver_banned"}).drop(columns="users_id")

    # Keep only trips with unbanned client and driver
    trips = trips[
        (trips["client_banned"] == "No") &
        (trips["driver_banned"] == "No")
    ]

    # Cancelled trip indicator
    trips["cancelled"] = trips["status"].str.startswith("cancelled").astype(int)

    # Calculate cancellation rate
    result = (
        trips.groupby("request_at")
        .agg(
            **{
                "Cancellation Rate": ("cancelled", "mean")
            }
        )
        .reset_index()
        .rename(columns={"request_at": "Day"})
    )

    result["Cancellation Rate"] = result["Cancellation Rate"].round(2)

    return result