package complexnumbers

import "math"

type Number struct {
	r float64
	i float64
}

func (n Number) Real() float64 {
	return n.r
}

func (n Number) Imaginary() float64 {
	return n.i
}

func (n1 Number) Add(n2 Number) Number {
	return Number{n1.r + n2.r, n1.i + n2.i}
}

func (n1 Number) Subtract(n2 Number) Number {
	return Number{n1.r - n2.r, n1.i - n2.i}
}

func (n1 Number) Multiply(n2 Number) Number {
	return Number{n1.r*n2.r - n1.i*n2.i, n1.r*n2.i + n2.r*n1.i}
}

func (n Number) Times(factor float64) Number {
	return Number{n.r * factor, n.i * factor}
}

func (n1 Number) Divide(n2 Number) Number {
	return Number{
		(n1.r*n2.r + n1.i*n2.i) / (n2.r*n2.r + n2.i*n2.i),
		(n1.i*n2.r - n1.r*n2.i) / (n2.r*n2.r + n2.i*n2.i),
	}
}

func (n Number) Conjugate() Number {
	return Number{n.r, -n.i}
}

func (n Number) Abs() float64 {
	return math.Sqrt(n.r*n.r + n.i*n.i)
}

func (n Number) Exp() Number {
	return Number{math.Cos(n.i), math.Sin(n.i)}.Times(math.Exp(n.r))
}
