class Solution:
    def twoSum(self, nums: list[int], target: int) -> list[int]:
        """Return the indexes of two elements of num that sum up to target.

        >>> Solution().twoSum([1, 5, 7, 4], 8)
        [2, 0]
        """
        indexes = {}
        for i, n in enumerate(nums):
            if n in indexes:
                if n + n == target:
                    return [indexes[n], i]
                else:
                    continue
            indexes[n] = i
        for i, n in enumerate(nums):
            right = target - n
            if right in indexes and indexes[right] != i:
                return [indexes[right], i]
        return []

if __name__ == "__main__":
    import doctest
    doctest.testmod()
