# Modul bazy danych

## Po co migracje
Dosc trudno jest utrzymywac spojny schemat bazy na wielu maszynach bez migracji.
Jeśli ktoś nie potrzebuje migracji to aktualna wersja pustej bazy zawsze jest w pliku db.sql w masterze. 
Rozwojowa na gałęzi `feature/db`.

## instalacja toolkitu (jako root)
```
curl -sL https://deb.nodesource.com/setup | bash > /dev/null
apt-get -y install nodejs
npm install -g db-migrate db-migrate-mysql
```

## konfiguracja
Skopiować dabatase.json.sample jako database.json i uzupelnic wartosci (wpisac na sztywno lub uzyc zmiennych srodowiskowych [opis ponizej]).

### zmienne srodowiskowe
Gdyby ktos nie wiedzial - nalezy dodac takie cos do ~/.bashrc.

```
export mysqluser="user"
export mysqlpasswd="haslo"
export mysqldb="nazwaBazy"
```

## zarzadzanie migracjami

migracja do najnowszej wersji
```
db-migrate up;
```

Migracja o jeden w dół
```
db-migrate down
```

Parametr `-c ` pozwala okreslic ile migracji chcemy zastosowac (w góre lub w dół).

## Problemy z migracjami
Gdy cos pojdzie nie tak to mamy najpewniej po bazie i musimy robic `db-migrate down` az nam powie, ze wiecej zrobic nie moze. Ewentualnie usunac baze, wyjdzie na to samo. 
Mimo wszystko nie powinno sie to zdarzyc. Gdyby sie zdarzylo prosze zalozyc ticket i przypisac go do @psrebniak;
