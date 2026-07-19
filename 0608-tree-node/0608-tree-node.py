import pandas as pd

def tree_node(tree: pd.DataFrame) -> pd.DataFrame:
    
    # Find all node values that appear as a parent
    parents = set(tree["p_id"].dropna())
    
    # Classify each node
    tree["type"] = tree.apply(
        lambda row: (
            "Root" if pd.isna(row["p_id"])
            else "Inner" if row["id"] in parents
            else "Leaf"
        ),
        axis=1
    )
    
    return tree[["id", "type"]]