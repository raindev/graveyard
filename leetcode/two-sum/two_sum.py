class Solution:
    def twoSum(self, nums: list[int], target: int) -> list[int]:
        """Return the indexes of two elements of num that sum up to target.

        >>> set(Solution().twoSum([1, 5, 7, 4], 8))
        {0, 2}

        >>> set(Solution().twoSum([2, 5, 7, 2], 4))
        {0, 3}
        """
        indexes = {}
        for i, n in enumerate(nums):
            complement = target - n
            if complement in indexes:
                return [indexes[complement], i]
            indexes[n] = i
        return []

if __name__ == "__main__":
    import doctest
    doctest.testmod()
