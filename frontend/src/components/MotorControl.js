import React, { useState } from 'react';
import axios from 'axios';

const MotorControl = () => {
  const [profile, setProfile] = useState('');

  const startProfile = async (profileName) => {
    try {
      const response = await axios.post('/api/motor/start-profile', { profileName });
      console.log(response.data);
      // TODO: Hier deditec API integrieren, um den Controller anzusprechen.
    } catch (error) {
      console.error('Fehler beim Starten des Profils', error);
    }
  };

  const handleButtonClick = (profileName) => {
    setProfile(profileName);
    startProfile(profileName);
  };

  return (
    <div>
      <h2>Fahrprofile</h2>
      <button onClick={() => handleButtonClick('Profile1')}>Fahrprofil 1</button>
      <button onClick={() => handleButtonClick('Profile2')}>Fahrprofil 2</button>
    </div>
  );
};

export default MotorControl;
