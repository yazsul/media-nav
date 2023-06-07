import React from 'react';
import Particle from "react-tsparticles";
import { loadFull } from 'tsparticles';

const Particles = () => {
    const particlesInit = React.useCallback(async engine => {
        console.log(engine);
        await loadFull(engine);
    }, []);

  return (
  <Particle
    className="particles"
    id="tsparticles"
    init={particlesInit}
    options={{
        background: {
            color: {
                value: "#0d47a1",
            },
        },
        fpsLimit: 120,
        particles: {
            color: {
                value: "#ffffff",
            },
            links: {
                color: "#ffffff",
                distance: 150,
                enable: true,
                opacity: 0.5,
                width: 1,
            },
         
            move: {
                directions: "none",
                enable: true,
                outModes: {
                    default: "bounce",
                },
                random: false,
                speed: 6,
                straight: false,
            },
            number: {
                density: {
                    enable: true,
                    area: 1000,
                },
                value: 80,
            },
            opacity: {
                value: 0.5,
            },
            shape: {
                type: "circle",
            },
            size: {
                value: { min: 1, max: 5 },
            },
        },
        detectRetina: true,
    }}
    />
    )
}

export default Particles