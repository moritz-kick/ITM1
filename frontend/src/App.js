import React from 'react';
import MotorControl from './components/MotorControl';
import EmergencyStop from './components/EmergencyStop';
import StatusDisplay from './components/StatusDisplay';
import PinLayout from './components/PinLayout';

function App() {
  return (
    <div className="App">
      <h1>Motorsteuerung für dryve 16 D1</h1>
      <MotorControl />
      <StatusDisplay />
      <PinLayout />
    </div>
  );
}

export default App;
