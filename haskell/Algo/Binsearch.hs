module Algo.Binsearch (binarySearch) where

import Prelude hiding (null, length, take, drop)
import Data.Vector (Vector, (!), null, length, take, drop)

binarySearch :: (Ord a) => Vector a -> a -> Maybe Int
binarySearch vec _ | null vec = Nothing
binarySearch vec x =
        let split  = length vec `div` 2
            m = vec ! split
            lvec = take split vec
            rvec = drop (split + 1) vec
        in case compare x m of EQ -> Just split
                               GT -> fmap (+(split + 1)) (binarySearch rvec x)
                               LT -> binarySearch lvec x
