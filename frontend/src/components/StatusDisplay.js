import React from 'react';
import axios from 'axios';

const EmergencyStop = () => {
  const handleEmergencyStop = async () => {
    try {
      const response = await axios.post('/api/motor/emergency-stop');
      console.log(response.data);
    } catch (error) {
      console.error('Fehler beim Not-Aus', error);
    }
  };

  return (
    <div>
      <h2>Not-Aus</h2>
      <button style={{ backgroundColor: 'red', color: 'white' }} onClick={handleEmergencyStop}>
        Not-Aus
      </button>
    </div>
  );
};

export default EmergencyStop;
