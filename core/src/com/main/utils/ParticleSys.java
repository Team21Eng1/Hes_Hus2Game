package com.main.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.main.entity.Entity;
import com.main.entity.Particle;


import java.util.ArrayList;
import java.util.Iterator;

public class ParticleSys {
    public ArrayList<Particle> particles;
    float timer,t;
    private Entity parent;
    public ParticleSys(Entity parent)
    {
        this.timer = 0.2f;
        this.t = 0;
        this.parent = parent;
        particles = new ArrayList<Particle>();
    }

    public void render(SpriteBatch batch){
        if (particles.size() > 0) {
            for (Particle p : particles) {
                batch.draw(p.getCurrentFrame(), p.worldX, p.worldY);

            }
        }
    };
    public Particle newSmokeParticle(char dir,int x,int y)
    {
        Particle pcl;
        switch (dir) //Directions opposite as dust will move opposite to direction
        {
            case 'U':
                pcl = new Particle(x,y,new Texture("Particles/SmokeDown.png"),this);
                pcl.Down();
                break;
            case 'D':
                pcl = new Particle(x,y,new Texture("Particles/SmokeUp.png"),this);
                pcl.Up();
                break;
            case 'L':
                pcl = new Particle(x,y,new Texture("Particles/SmokeRight.png"),this);
                pcl.Right();
                break;

            case 'R':
                pcl = new Particle(x,y,new Texture("Particles/SmokeLeft.png"),this);
                pcl.Left();
                break;
            default:
                pcl = null;
        }

        return pcl;
    }

    public void update(float delta)
    {

        t+=delta;
        if (t>timer && parent.isMoving)
        {
            t=0;
            //place particle
            particles.add(newSmokeParticle(parent.dir, (int) parent.worldX, (int) parent.worldY));

        }
        Iterator<Particle> iterator = particles.iterator();
        while (iterator.hasNext()) {
            Particle p = iterator.next();
            p.update(delta);
            if (p.currentAnimation.isAnimationFinished(p.stateTime))
            {
                iterator.remove();
            }


        }

    }

}
