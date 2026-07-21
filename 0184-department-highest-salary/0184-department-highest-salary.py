import pandas as pd

def department_highest_salary(employee: pd.DataFrame, department: pd.DataFrame) -> pd.DataFrame:
    # Find the maximum salary in each department
    max_salary = employee.groupby("departmentId")["salary"].transform("max")

    # Keep employees whose salary equals the department maximum
    result = employee[employee["salary"] == max_salary]

    # Join with department table
    result = result.merge(
        department,
        left_on="departmentId",
        right_on="id"
    )

    # Return required columns
    return result[["name_y", "name_x", "salary"]].rename(
        columns={
            "name_y": "Department",
            "name_x": "Employee",
            "salary": "Salary"
        }
    )