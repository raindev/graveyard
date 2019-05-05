use std::cmp::max;
use std::cmp::min;
use std::io;
use std::io::BufRead;
use std::io::BufReader;
use std::io::Read;
use std::io::Write;
use std::ops::Range;
use std::ops::RangeInclusive;
use std::result::Result;

struct Stations {
    ids: Range<u32>,
    clients: u32,
    left: Option<Box<Stations>>,
    right: Option<Box<Stations>>,
}

fn main() {
    let stdin = io::stdin();
    let stdout = io::stdout();
    base_stations(&mut stdin.lock(), &mut stdout.lock());
}

fn base_stations(input: &mut Read, out: &mut Write) {
    println!("base_stations");
    let mut lines = BufReader::new(input).lines().map(Result::unwrap);
    println!("lines");
    let _stations: u32 = lines.next().unwrap().parse().unwrap();
    println!("_stations");

    let clients_str = lines.next().unwrap();
    let clients: Vec<u32> = clients_str
        .split(' ')
        .inspect(|token| print!("t:{}, ", token))
        .map(str::parse)
        .map(Result::unwrap)
        .collect();
    println!("clients");
    println!("number of stations: {}", clients.len());
    println!("connected clients:\n{:?}", clients);
    let mut stations = Stations {
        ids: 1..clients.len() as u32 + 1,
        clients: 0,
        left: None,
        right: None,
    };
    initialize(&mut stations, &clients);

    let _events: u32 = lines.next().unwrap().parse().unwrap();

    for line in lines {
        println!("{}", line);
        let mut command = line.split(' ');
        match command.next().unwrap() {
            "LEAVE" => update(
                &mut stations,
                command.next().unwrap().parse().unwrap(),
                false,
            ),
            "ENTER" => update(
                &mut stations,
                command.next().unwrap().parse().unwrap(),
                true,
            ),
            "COUNT" => {
                let start = command.next().unwrap().parse().unwrap();
                let mut end = command.next().unwrap().parse().unwrap();
                writeln!(out, "{}", count(&stations, start..=end)).unwrap();
            }
            _ => panic!(),
        }
    }
}

fn initialize(stations: &mut Stations, clients: &[u32]) {
    if stations.ids.start + 1 == stations.ids.end {
        // Stations are indexed starting from 1.
        stations.clients = clients[(stations.ids.start - 1) as usize];
        // Range with one ID doesn't need to be split anymore.
        return;
    }

    let mut left = Box::new(Stations {
        ids: stations.ids.start..(stations.ids.start + stations.ids.end) / 2,
        clients: 0,
        left: None,
        right: None,
    });
    initialize(&mut left, clients);
    stations.left = Some(left);

    stations.right = Some(Box::new(Stations {
        // Start from the end index of left since range is exclusive.
        ids: (stations.ids.start + stations.ids.end) / 2..stations.ids.end,
        clients: 0,
        left: None,
        right: None,
    }));
    initialize(stations.right.as_mut().unwrap(), clients);

    stations.clients =
        stations.left.as_ref().unwrap().clients + stations.right.as_ref().unwrap().clients;
    println!("{:?}: {}", stations.ids, stations.clients);
}

fn update(stations: &mut Stations, id: u32, enter: bool) {
    if enter {
        stations.clients += 1;
    } else {
        stations.clients -= 1;
    }
    if stations.ids.start == id && stations.ids.start + 1 == stations.ids.end {
        println!("removed from {}, {} left", id, stations.clients);
        return;
    }

    if id < stations.left.as_ref().unwrap().ids.end {
        update(stations.left.as_mut().unwrap(), id, enter);
    } else {
        update(stations.right.as_mut().unwrap(), id, enter);
    }
}

fn count(stations: &Stations, range: RangeInclusive<u32>) -> u32 {
    println!(
        "{}..{} count {} {}",
        stations.ids.start,
        stations.ids.end,
        range.start(),
        range.end()
    );
    if stations.ids.start == *range.start() && stations.ids.end - 1 == *range.end() {
        stations.clients
    } else {
        let left_range = &stations.left.as_ref().unwrap().ids;
        let left_count = if *range.start() < left_range.end {
            count(
                &stations.left.as_ref().unwrap(),
                *range.start()..=min(*range.end(), left_range.end - 1),
            )
        } else {
            0
        };
        let right_range = &stations.right.as_ref().unwrap().ids;
        let right_count = if *range.end() >= left_range.end {
            count(
                &stations.right.as_ref().unwrap(),
                max(*range.start(), right_range.start)..=*range.end(),
            )
        } else {
            0
        };
        left_count + right_count
    }
}

#[cfg(test)]
mod tests {

    use super::base_stations;

    #[test]
    fn odd_station_number() {
        let mut input = std::io::BufReader::new(
            "5
2 0 2 3 1
9
COUNT 2 4
ENTER 2
LEAVE 1
COUNT 2 4
LEAVE 5
COUNT 4 5
COUNT 1 2
ENTER 2
COUNT 1 2"
                .as_bytes(),
        );
        let mut output = Vec::new();
        base_stations(&mut input, &mut output);
        assert_eq!(
            "5
6
3
2
3
",
            String::from_utf8(output).unwrap()
        );
    }

    #[test]
    fn even_station_number() {
        let mut input = std::io::BufReader::new(
            "4
2 3 1 0
5
COUNT 1 4
ENTER 4
COUNT 1 4
LEAVE 1
COUNT 1 3"
                .as_bytes(),
        );
        let mut output = Vec::new();
        base_stations(&mut input, &mut output);
        assert_eq!(
            "6
7
5
",
            String::from_utf8(output).unwrap()
        );
    }

    #[test]
    fn one_station() {
        let mut input = std::io::BufReader::new(
            "1
2
3
COUNT 1 1
ENTER 1
COUNT 1 1"
                .as_bytes(),
        );
        let mut output = Vec::new();
        base_stations(&mut input, &mut output);
        assert_eq!(
            "2
3
",
            String::from_utf8(output).unwrap()
        );
    }

}
