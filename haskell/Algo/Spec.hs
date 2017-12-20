module Main (main) where

import Test.Hspec (Spec, hspec, describe)
import qualified Algo.BinsearchSpec
import qualified Algo.QuickSortSpec

main :: IO ()
main = hspec spec

spec :: Spec
spec = do
  describe "Binary search"  Algo.BinsearchSpec.spec
  describe "Quick sort"     Algo.QuickSortSpec.spec
