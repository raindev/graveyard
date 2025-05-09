import Foundation

enum TranscriptionError: Error {
  case invalidNucleotide(message: String)
}

enum DNANucleobase: Character {
  case guanine = "G"
  case adenine = "A"
  case cytosine = "C"
  case thymine = "T"

  func transcribe() -> RNANucleobase {
    switch self {
    case .guanine: return .cytosine;
    case .cytosine: return .guanine;
    case .thymine: return .adenine;
    case .adenine: return .uracil;
    }
  }
}

enum RNANucleobase: Character {
  case guanine = "G"
  case adenine = "A"
  case cytosine = "C"
  case uracil = "U"
}

struct Nucleotide {
  let strand: [DNANucleobase]

  init(_ rna: String) throws {
    strand = try rna.map { 
      guard let n = DNANucleobase(rawValue: $0) else {
        let message = "\($0) is not a valid Nucleotide";
        throw TranscriptionError.invalidNucleotide(message: message)
      }
      return n
    }
  }

  func complementOfDNA() -> String {
    strand.map { String($0.transcribe().rawValue) }.joined()
  }
}
