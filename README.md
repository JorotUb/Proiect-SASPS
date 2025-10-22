# 🧟‍♂️ Sistem 2D top-down de tip „Zombie Survivors” bazat pe Design Patterns

## 📖 Descriere generală

Acest proiect reprezintă o aplicație 2D de tip joc „top-down shooter”, dezvoltată în **Java**, în care jucătorul controlează un personaj ce trebuie să supraviețuiască valurilor de zombi.  
Scopul proiectului este de a evidenția **impactul utilizării design pattern-urilor** în arhitectura software, prin compararea a două versiuni ale jocului:

- o versiune **fără design patterns**, cu o arhitectură monolitică, dificil de extins;
- o versiune **bazată pe design patterns**, modulară și extensibilă.

Proiectul este realizat în cadrul disciplinei **„Design Patterns”** și urmărește să demonstreze avantajele principiilor OOP în dezvoltarea aplicațiilor interactive.

---

## 🎯 Obiective

- Dezvoltarea unui **joc 2D top-down** simplu, cu entități dinamice (jucător, zombi, proiectile);
- Aplicarea mai multor **design patterns clasice** pentru a îmbunătăți structura codului;
- Analizarea comparativă a celor două versiuni ale aplicației;
- Măsurarea metricilor de calitate software: modularitate, claritate, mentenabilitate, complexitate.

---

## 🧩 Design Patterns utilizate

| Pattern | Rol în joc | Descriere |
|----------|-------------|-----------|
| **Command** | Controlul jucătorului | Fiecare acțiune (mișcare, atac, schimbare armă) este implementată ca o comandă separată. |
| **Strategy** | Comportamentul inamicilor | Permite definirea diferitelor tipuri de AI (zombi lenti, rapizi, agresivi). |
| **State** | Stările personajului jucător | Modelează stările Player-ului: Normal, Attacking, Injured, Dead. |
| **Observer** | Actualizarea interfeței | Actualizează UI-ul la schimbarea scorului, sănătății sau numărului de inamici. |
| **Factory Method** | Crearea inamicilor și a proiectilelor | Permite generarea dinamică a entităților cu caracteristici diferite. |

---
## Tehnologii folosite

- **Limbaj:** Java 17+  
- **Framework grafic:** JavaFX (sau LibGDX)  
- **IDE:** IntelliJ IDEA / Eclipse  
- **Control versiune:** Git + GitHub  
- **Instrumente analiză:** SonarLint, IntelliJ Metrics  

---
## Rezultate așteptate

- Arhitectură modulară și ușor de extins;
- Cod mai clar și ușor de întreținut;
- Demonstrarea beneficiilor aduse de design patterns în aplicațiile interactive.
