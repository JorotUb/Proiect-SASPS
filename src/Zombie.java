public class Zombie {
    private double  x, y;
    private int health = 50;

    public Zombie(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void update(Player player) {
        // miscare simpla catre player
        if (x < player.getX()) x++;
        if (x > player.getX()) x--;
        if (y < player.getY()) y++;
        if (y > player.getY()) y--;
    }

    public boolean isDead() {
        return health <= 0;
    }

    public void takeDamage(int dmg) {
        health -= dmg;
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public int getHealth() { return health; }
}