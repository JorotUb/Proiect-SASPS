# 🛸 Sistem interactiv 2D de simulare și control al unei drone, bazat pe Design Patterns

## 📖 Descriere generală

Acest proiect reprezintă o aplicație 2D de tip joc, dezvoltată în **Java**, care simulează controlul unei drone.  
Scopul proiectului este de a evidenția **impactul design pattern-urilor** asupra arhitecturii software, prin realizarea unei comparații între două versiuni ale aceluiași sistem:
- o versiune **fără design patterns** (abordare procedurală, cod monolitic);
- o versiune **cu design patterns**, ce respectă principiile OOP și promovează modularitatea și extensibilitatea codului.

Proiectul face parte din activitatea de laborator aferentă disciplinei **„Design Patterns”** și este realizat în echipă.

---

## Obiective

- Implementarea unui **joc 2D simplu** în care utilizatorul controlează o dronă;
- Utilizarea unui set de **design patterns** relevante pentru arhitectura jocului;
- Compararea celor două implementări (cu / fără patterns);
- Analiza metricilor de calitate software: modularitate, complexitate, mentenabilitate.

---

## Design Patterns utilizate

| Pattern | Rol în aplicație | Descriere |
|----------|------------------|------------|
| **Command** | Controlul acțiunilor dronei | Fiecare acțiune (sus, jos, trage) este o comandă separată; facilitează extinderea sistemului de control. |
| **Strategy** | Mișcarea dronei | Permite schimbarea dinamică a comportamentului (normal, agresiv, defensiv). |
| **State** | Starea curentă a dronei | Modelează stările dronei: Idle, Moving, Attacking, Damaged, Crashed. |
| **Observer** | Actualizarea interfeței grafice | Notifică automat componentele UI (energie, scor, status). |
| **Factory Method** | Crearea inamicilor și proiectilelor | Simplifică extinderea sistemului prin crearea dinamică a entităților. |

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
