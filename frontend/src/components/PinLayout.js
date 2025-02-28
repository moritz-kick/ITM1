import React, { useState } from 'react';

const digitalInputsData = [
  { id: 1, binär: "Bit 0", tTeach: "Bit 0", takt: "Takt" },
  { id: 2, binär: "Bit 1", tTeach: "Bit 1", takt: "Richtung" },
  { id: 3, binär: "Bit 2", tTeach: "Bit 2", takt: "-" },
  { id: 4, binär: "Bit 3", tTeach: "Tippen links", takt: "-" },
  { id: 5, binär: "Bit 4", tTeach: "Tippen rechts", takt: "-" },
  { id: 6, binär: "Start", tTeach: "Start/Teach", takt: "-" },
  { id: 7, binär: "Freigabe", tTeach: "Freigabe", takt: "Freigabe" },
  { id: 8, binär: "Endlagenschalter positiv", tTeach: "Endlagenschalter positiv", takt: "Endlagenschalter positiv" },
  { id: 9, binär: "Endlagenschalter negativ", tTeach: "Endlagenschalter negativ", takt: "Endlagenschalter negativ" },
  { id: 10, binär: "Stopp/Reset", tTeach: "Stopp/Reset", takt: "Reset" }
];

const schrauberOutputsData = [
  { id: 1, label: "Programm Auswahl 1" },
  { id: 2, label: "Programm Auswahl 2" },
  { id: 3, label: "STOPMOTOR" },
  { id: 4, label: "START" },
  { id: 5, label: "IN ANG" },
  { id: 6, label: "IN RST" }
];

const PinLayout = () => {
  // Zustände für digitale Eingänge (10 Pins)
  const [digitalInputs, setDigitalInputs] = useState(Array(10).fill(false));
  // Zustände für Schrauber-Ausgänge (6 Pins)
  const [schrauberOutputs, setSchrauberOutputs] = useState(Array(6).fill(false));

  const toggleDigitalInput = (index) => {
    const newStates = [...digitalInputs];
    newStates[index] = !newStates[index];
    setDigitalInputs(newStates);
  };

  const toggleSchrauberOutput = (index) => {
    const newStates = [...schrauberOutputs];
    newStates[index] = !newStates[index];
    setSchrauberOutputs(newStates);
  };

  const toggleAll = () => {
    setDigitalInputs(digitalInputs.map(state => !state));
    setSchrauberOutputs(schrauberOutputs.map(state => !state));
  };

  const getIndicatorStyle = (state) => ({
    width: '20px',
    height: '20px',
    borderRadius: '50%',
    backgroundColor: state ? 'green' : 'red',
    display: 'inline-block',
    marginRight: '10px'
  });

  return (
    <div>
      <h2>Pin-Layout</h2>
      <h3>Dryve D1 Digitale Eingänge (X2)</h3>
      <table border="1" cellPadding="5">
        <thead>
          <tr>
            <th>Pin Nr.</th>
            <th>Binär</th>
            <th>Tipp/Teach</th>
            <th>Takt/Richtung</th>
            <th>Status</th>
            <th>Toggle</th>
          </tr>
        </thead>
        <tbody>
          {digitalInputsData.map((pin, index) => (
            <tr key={pin.id}>
              <td>{pin.id}</td>
              <td>{pin.binär}</td>
              <td>{pin.tTeach}</td>
              <td>{pin.takt}</td>
              <td>
                <span style={getIndicatorStyle(digitalInputs[index])}></span>
                {digitalInputs[index] ? 'An' : 'Aus'}
              </td>
              <td>
                <button onClick={() => toggleDigitalInput(index)}>Toggle</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      <h3>Schrauber Digitale Ausgänge</h3>
      <table border="1" cellPadding="5">
        <thead>
          <tr>
            <th>Output Nr.</th>
            <th>Bezeichnung</th>
            <th>Status</th>
            <th>Toggle</th>
          </tr>
        </thead>
        <tbody>
          {schrauberOutputsData.map((output, index) => (
            <tr key={output.id}>
              <td>{index + 10}</td>
              <td>{output.label}</td>
              <td>
                <span style={getIndicatorStyle(schrauberOutputs[index])}></span>
                {schrauberOutputs[index] ? 'An' : 'Aus'}
              </td>
              <td>
                <button onClick={() => toggleSchrauberOutput(index)}>Toggle</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      <button onClick={toggleAll} style={{ marginTop: '20px', padding: '10px 20px' }}>
        Test: Alle umschalten
      </button>
    </div>
  );
};

export default PinLayout;
