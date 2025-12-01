public class Projectile {
    private double x, y;
    private double vx, vy;
    private int damage = 10;

    public Projectile(double x, double y, double vx, double vy){
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public int getDamage() { return damage; }

    public void update() {
        x += vx;
        y += vy;
    }

    // metoda de coliziune cu Zombie
    public boolean collidesWith(Zombie z) {
        double dx = x - z.getX();
        double dy = y - z.getY();
        double distance = Math.sqrt(dx*dx + dy*dy);
        return distance < 15; // ajustați după dimensiunea grafică
    }
}