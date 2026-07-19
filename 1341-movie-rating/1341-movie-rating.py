import pandas as pd

def movie_rating(movies: pd.DataFrame,
                 users: pd.DataFrame,
                 movieRating: pd.DataFrame) -> pd.DataFrame:

    # User who rated the most movies
    top_user = (
        movieRating.groupby("user_id")
        .size()
        .reset_index(name="cnt")
        .merge(users, on="user_id")
        .sort_values(["cnt", "name"], ascending=[False, True])
        .iloc[0]["name"]
    )

    # Movie with the highest average rating in February 2020
    top_movie = (
        movieRating[
            (movieRating["created_at"] >= "2020-02-01") &
            (movieRating["created_at"] < "2020-03-01")
        ]
        .groupby("movie_id")["rating"]
        .mean()
        .reset_index(name="avg_rating")
        .merge(movies, on="movie_id")
        .sort_values(["avg_rating", "title"], ascending=[False, True])
        .iloc[0]["title"]
    )

    return pd.DataFrame({"results": [top_user, top_movie]})