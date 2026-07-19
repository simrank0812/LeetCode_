import pandas as pd

def students_and_examinations(
    students: pd.DataFrame,
    subjects: pd.DataFrame,
    examinations: pd.DataFrame
) -> pd.DataFrame:
    
    # Create all student-subject combinations (Cartesian product)
    all_pairs = students.merge(subjects, how="cross")
    
    # Count exam attendances for each student-subject pair
    exam_count = (
        examinations
        .groupby(["student_id", "subject_name"])
        .size()
        .reset_index(name="attended_exams")
    )
    
    # Merge with all combinations
    result = (
        all_pairs
        .merge(exam_count, on=["student_id", "subject_name"], how="left")
        .fillna({"attended_exams": 0})
    )
    
    # Convert to integer and sort
    result["attended_exams"] = result["attended_exams"].astype(int)
    
    return result.sort_values(
        ["student_id", "subject_name"]
    ).reset_index(drop=True)