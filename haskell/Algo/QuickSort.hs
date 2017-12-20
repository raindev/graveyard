module Algo.QuickSort (qsort) where

qsort :: (Ord a) => [a] -> [a]
qsort [] = []
qsort (pivot:xs) = (qsort (filter (<pivot) xs))
        ++ [pivot]
        ++ (qsort (filter (>pivot) xs))
