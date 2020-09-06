package io.mbab.sda.groupproject.menu.action;

import io.mbab.sda.groupproject.entity.Country;
import io.mbab.sda.groupproject.entity.Player;
import io.mbab.sda.groupproject.entity.Team;
import io.mbab.sda.groupproject.menu.CustomScanner;
import io.mbab.sda.groupproject.menu.MenuActionContext;
import io.mbab.sda.groupproject.repository.CountryRepository;
import io.mbab.sda.groupproject.repository.PlayerRepository;
import io.mbab.sda.groupproject.repository.TeamRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;



@RequiredArgsConstructor
public class CreatePlayerAction implements MenuAction {

  private final CustomScanner scanner;
  private final MenuActionContext ctx;
  private final CountryRepository countryRepository;
  private final PlayerRepository playerRepository;
  private final TeamRepository teamRepository;

    @Override
  public void execute() {


    System.out.println("!!! DODAJESZ PIŁKARZA !!!");
    System.out.println("--> Wciśnięcie '0' powoduję powtór do menu głównego <--");
    System.out.println("Podaj imie gracza:");
    String firstName = scanner.nextLine();
    if (pressedZero(firstName)){
      return;
    }

    System.out.println("!!! DODAJESZ PIŁKARZA !!!");
    System.out.println("Podaj nazwisko gracza:");
    String lastName = scanner.nextLine();
    if (pressedZero(lastName)) {
      return;
    }

    System.out.println("!!! DODAJESZ PIŁKARZA !!!");
    System.out.println("Podaj date urodzenia gracza:");
    String dateOfBirth = scanner.nextLine();
    if (pressedZero(dateOfBirth)) {
      return;
    }

    Team team = pickTeam();

    System.out.println("!!! DODAJESZ PIŁKARZA !!!");
    System.out.println("Podaj kraj pochodzenia gracza:");

    String countryName = scanner.nextLine();
    Country country = null;

    if (pressedZero(countryName)) {
      return;
    }

    country = countryRepository.findByName(countryName);

    if (country == null) {
      System.out.println("!!! DODAJESZ PIŁKARZA !!!");
      System.out.println("Tworzysz nowy kraj: " + countryName);

    } else {
      countryRepository.create(country);
    }

    Player player =
        Player.builder()
            .firstName(firstName)
            .lastName(lastName)
            .dateOfBirth(dateOfBirth)
            .country(country)
            .team(team)
            .build();

    playerRepository.create(player);

    System.out.println("Dodałeś piłkarza o danych: " + firstName + " " + lastName);
    if (team == null) {
      System.out.println("Bez drużyny");
    }
    else {
      System.out.println(team.getName());
    }
    System.out.println(dateOfBirth);
    System.out.println(countryName);
  }

  private boolean pressedZero(String input) {
    if (input.equals("0")) {
        ctx.use(MainAction.class).execute();
      return true;
    }
    return false;
  }

  private Team pickTeam(){
    System.out.println("!!! DODAJESZ PIŁKARZA !!!");
    System.out.println("Podaj drużynę w której gracz występuję:");
    System.out.println("Pusty znak - piłkarz jest obecnie bez drużyny");
    String teamName = scanner.nextLine();
    if (pressedZero(teamName)) {
      return null;
    }
    if ("".equals(teamName.trim())) {
      return null;
    }

    Team team = teamRepository.findByName(teamName);

    if (team != null) {
      return team;
    }
    System.out.println("Nie istnieje taka drużyna, wprowadź nazwę raz jeszcze");

    return pickTeam();
  }
}
