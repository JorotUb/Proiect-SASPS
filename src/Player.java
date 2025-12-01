public class Player {
    private double x, y;
    private int health = 100;

    public Player(double x, double y) { this.x = x; this.y = y; }


    public void update() {
        // input simplu: W/A/S/D pentru miscare
        // spatiu pentru atac (creaza un Projectile)
    }

    public boolean collidesWith(Zombie z) {
        return Math.abs(x - z.getX()) < 20 && Math.abs(y - z.getY()) < 20;
    }

    // Adauga aceste metode pentru ca Zombie sa poata sti poziția playerului
    public double getX() { return x; }
    public double getY() { return y; }
    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
    public int getHealth() { return health; }
    public void takeDamage(int dmg) { health -= dmg; }
}