import pandas as pd

def find_employees(employee: pd.DataFrame) -> pd.DataFrame:
    merged = employee.merge(
        employee,
        left_on="managerId",
        right_on="id",
        suffixes=("_emp", "_mgr")
    )

    return merged.loc[
        merged["salary_emp"] > merged["salary_mgr"],
        ["name_emp"]
    ].rename(columns={"name_emp": "Employee"})