# winery
Repozytorium projektu "Winnica"

Witam serdecznie kolegow winiarzy!

## Jak tego uzywac?

Masz przed soba nasze wspolne repozytorium i byc moze zadajesz sobie powyzsze pytanie.
Spiesze z odpowiedzia.

### Wstepna Konfiguracja

Odpal terminal i przejdz do jakego folderu, np. ~/Documents.
Wpisz tam 'git clone git@github.com/mpyzik/winery.git'.
Efekt powinien być taki, ze skopiowales wlasnie nasze repo na swoj komputer.
Ponadto, wie juz ono, gdzie jest jego oryginalna wersja.

### Co dalej?

Masz wlasny klucz RSA podpiety do konta? Swietnie, git bedzie mogl cie laczyc z repo przez protokol ssh.

Dowiedz sie, co sie stanie, gdy odpalisz program *git* z argumentami:
 * branch - tworzy/usuwa galaz (raczej nie uzywac samowolnie, by nie robic balaganu)
 * checkout - zmienia aktywna (aktualnie pokazywana) galaz
 * add - zaznacza pliki, ktorych zminay chcesz zaraz zapamietac
 * commit - po uzyciu add mozna zapisac zmiany ta komeda i nadac im zbiorczy opis
 * push - wysyla zapamietane lokalne zmiany do zdalnego repo (tego tutaj)
 * fetch - sciaga zmiany z glownego repo do lokalnego (uzyj tego przed rozpoczeciem pracy)
 * pull - to co fetch, ale jeszcze od razu przechodzi do nowej wersji (o ile sie da tak latwo)

Niestety, czesem dwie osoby zmienia ten sam plik. Do takich nieszczesc wymyslono ponizsze narzedzia:
 * merge - laczenie skonfliktowanych wersji projektu (grubsza sprawa, obys nie musial z tym walczyc)
 * rebase - do rozwiazywania latwych, wrecz pozornych konfliktow (choc moze tez napsuc)

## Galezie

Doswiadczenie uczy, ze kazdy "feature" powinien byc rozwijany w innej galezi, a zatem uprasza sie, by:
 * nie rzucac pull request na galaz master bez dobrego powodu - ta galaz jest glowna i ma zawierac tylko stabilny kod
 * kazda z dwoch grup miala swoja galaz, ktorych zawartosc bedzie czasem synchronizowana z masterem
 * kazda z podgrup pracujacych nad pewna fukcjonalnoscia miala oddzielna galaz, synchronizowana z galezia grupy raczej pod koniec prac nad funkcjonalnoscia, a nie co chwile
