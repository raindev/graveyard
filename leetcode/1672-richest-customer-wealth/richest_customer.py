class Solution:
    def maximumWealth(self, accounts: List[List[int]]) -> int:
        return max(sum(customer_account) for customer_account in accounts)
