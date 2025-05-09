pub fn raindrops(n: i32) -> String {
    let drop = |div, s| {
        if n % div == 0 { s } else { "" }
    };
    match (String::new()
           + drop(3, "Pling")
           + drop(5, "Plang")
           + drop(7, "Plong")).as_ref() {
        "" => n.to_string(),
        s => s.to_string()
    }
}
