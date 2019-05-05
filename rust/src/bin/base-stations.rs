use std::cmp::max;
use std::cmp::min;
use std::io;
use std::io::BufRead;
use std::io::BufReader;
use std::io::Read;
use std::io::Write;
use std::ops::RangeInclusive;
use std::result::Result;

struct Stations {
    ids: RangeInclusive<u32>,
    clients: u32,
    left: Option<Box<Stations>>,
    right: Option<Box<Stations>>,
}

impl Stations {
    fn new(ids: RangeInclusive<u32>) -> Stations {
        Stations {
            ids,
            clients: 0,
            left: None,
            right: None,
        }
    }
}

fn main() {
    let stdin = io::stdin();
    let stdout = io::stdout();
    base_stations(&mut stdin.lock(), &mut stdout.lock());
}

fn base_stations(input: &mut Read, output: &mut Write) {
    let mut lines = BufReader::new(input).lines().map(Result::unwrap);
    lines.next(); // skip number of stations
    let clients: Vec<u32> = lines
        .next()
        .unwrap()
        .split(' ')
        .map(str::parse)
        .map(Result::unwrap)
        .collect();
    let mut stations = Stations::new(1..=clients.len() as u32);
    initialize(&mut stations, &clients);
    lines.next(); // skip number of commands
    for line in lines {
        let mut command_tokens = line.split(' ');
        let command = command_tokens.next().unwrap();
        let id = command_tokens.next().unwrap().parse().unwrap();
        match command {
            "LEAVE" => update(&mut stations, id, -1),
            "ENTER" => update(&mut stations, id, 1),
            "COUNT" => {
                let end_id = command_tokens.next().unwrap().parse().unwrap();
                writeln!(output, "{}", count(&stations, id..=end_id)).unwrap();
            }
            _ => panic!(),
        }
    }
}

fn initialize(stations: &mut Stations, clients: &[u32]) {
    if stations.ids.start() == stations.ids.end() {
        // Stations are indexed starting from 1.
        stations.clients = clients[(*stations.ids.start() - 1) as usize];
        // Range with one ID doesn't need to be split anymore.
        return;
    }
    let middle = (*stations.ids.start() + *stations.ids.end()) / 2;
    stations.left = Some(Box::new(Stations::new(*stations.ids.start()..=middle)));
    initialize(stations.left.as_mut().unwrap(), clients);
    stations.right = Some(Box::new(Stations::new(middle + 1..=*stations.ids.end())));
    initialize(stations.right.as_mut().unwrap(), clients);
    stations.clients =
        stations.left.as_ref().unwrap().clients + stations.right.as_ref().unwrap().clients;
}

fn update(stations: &mut Stations, id: u32, count: i32) {
    stations.clients = (stations.clients as i32 + count) as u32;
    if *stations.ids.start() == id && stations.ids.start() == stations.ids.end() {
        return;
    }
    if id <= *stations.left.as_ref().unwrap().ids.end() {
        update(stations.left.as_mut().unwrap(), id, count);
    } else {
        update(stations.right.as_mut().unwrap(), id, count);
    }
}

fn count(stations: &Stations, range: RangeInclusive<u32>) -> u32 {
    if stations.ids.start() == range.start() && stations.ids.end() == range.end() {
        return stations.clients;
    }
    let mut result = 0;
    let left_range = &stations.left.as_ref().unwrap().ids;
    if range.start() <= left_range.end() {
        result += count(
            &stations.left.as_ref().unwrap(),
            *range.start()..=min(*range.end(), *left_range.end()),
        );
    }
    let right_range = &stations.right.as_ref().unwrap().ids;
    if range.end() >= right_range.start() {
        result += count(
            &stations.right.as_ref().unwrap(),
            max(*range.start(), *right_range.start())..=*range.end(),
        );
    }
    result
}

#[cfg(test)]
mod tests {

    use super::base_stations;

    #[test]
    fn odd_station_number() {
        let mut input = "5
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
            .as_bytes();
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
        let mut input = "4
2 3 1 0
5
COUNT 1 4
ENTER 4
COUNT 1 4
LEAVE 1
COUNT 1 3"
            .as_bytes();
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
        let mut input = "1
2
3
COUNT 1 1
ENTER 1
COUNT 1 1"
            .as_bytes();
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
