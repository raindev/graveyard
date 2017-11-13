module Algo.Binsearch (binarySearch) where

binarySearch :: (Ord a) => [a] -> a -> Maybe Int
binarySearch [] _ = Nothing
binarySearch xs x =
        let split  = length xs `div` 2
            m = xs !! split
            hxs = snd $ splitAt (split + 1) xs
            lxs = fst $ splitAt split xs
        in case compare x m of EQ -> Just split
                               GT -> fmap (+(split + 1)) (binarySearch hxs x)
                               LT -> binarySearch lxs x
