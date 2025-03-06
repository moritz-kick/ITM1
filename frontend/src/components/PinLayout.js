import React, { useState, useEffect } from 'react';
import axios from 'axios';

const outputsData = [
    // Dryve D1 Outputs (0-7)
    { id: 0, label: "DI1 - Referenzfahrt", group: "dryve" },
    { id: 1, label: "DI4 - Tippen links", group: "dryve" },
    { id: 2, label: "DI5 - Tippen rechts", group: "dryve" },
    { id: 3, label: "DI6 - Start/Teach", group: "dryve" },
    { id: 4, label: "DI7 - Freigabe", group: "dryve" },
    { id: 5, label: "DI8 - Endlagenschalter positiv", group: "dryve" },
    { id: 6, label: "DI9 - Endlagenschalter negativ", group: "dryve" },
    { id: 7, label: "DI10 - Stopp/Reset", group: "dryve" },
    // Schrauber Outputs (8-12)
    { id: 8, label: "Programm Auswahl 1", group: "schrauber" },
    { id: 9, label: "Programm Auswahl 2", group: "schrauber" },
    { id: 10, label: "STOPMOTOR", group: "schrauber" },
    { id: 11, label: "START", group: "schrauber" },
    { id: 12, label: "IN ANG", group: "schrauber" },
    { id: 13, label: "IN RST", group: "schrauber" },
    // Druckluft Outputs (14-15)
    { id: 14, label: "Greifer", group: "druckluft" },
    { id: 15, label: "Magnetheber an", group: "druckluft" },
];

const inputsData = [
    // Dryve D1 Inputs (0-2)
    { id: 0, label: "DI1 - Referenzfahrt", group: "dryve" },
    { id: 1, label: "DI4 - Tippen links", group: "dryve" },
    { id: 2, label: "DI5 - Tippen rechts", group: "dryve" },
    // Schrauber Inputs (3-5)
    { id: 3, label: "37 - STOP", group: "schrauber" },
    { id: 4, label: "39 - END PR", group: "schrauber" },
    { id: 5, label: "42 - MOTOR ON", group: "schrauber" },
    // Druckluft Inputs (6-10)
    { id: 6, label: "Greifer Endschalter positiv", group: "druckluft" },
    { id: 7, label: "Greifer Endschalter negativ", group: "druckluft" },
    { id: 8, label: "Magnetheber Endschalter positiv", group: "druckluft" },
    { id: 9, label: "Magnetheber Endschalter negativ", group: "druckluft" },
    { id: 10, label: "Näherungsschalter Greifer", group: "druckluft" },
    // Unbeschriftete Inputs (11-15)
    { id: 11, label: "-", group: "none" },
    { id: 12, label: "-", group: "none" },
    { id: 13, label: "-", group: "none" },
    { id: 14, label: "-", group: "none" },
    { id: 15, label: "-", group: "none" }
];

const PinLayout = () => {
    const [inputs, setInputs] = useState(Array(16).fill(false));
    const [outputs, setOutputs] = useState(Array(16).fill(false));

    // WebSocket für Echtzeit-Updates der Inputs
    useEffect(() => {
        const ws = new WebSocket('ws://localhost:3000');
        ws.onmessage = (event) => {
            const data = JSON.parse(event.data);
            if (data.type === 'inputStatus') {
                setInputs(data.states);
            }
        };
        return () => ws.close();
    }, []);

    // Umschalten eines einzelnen Outputs
    const toggleOutput = async (index) => {
        const newState = !outputs[index];
        const newOutputs = [...outputs];
        newOutputs[index] = newState;
        setOutputs(newOutputs);

        try {
            await axios.post('/api/motor/set-output', { pin: index, state: newState ? 1 : 0 });
        } catch (error) {
            console.error('Fehler beim Setzen des Outputs:', error);
        }
    };

    // Umschalten aller Outputs (Backend übernimmt die Logik)
    const toggleAllOutputs = async () => {
        try {
            const response = await axios.post('/api/motor/toggle-all');
            console.log(response.data);
        } catch (error) {
            console.error('Fehler beim Umschalten aller Outputs:', error);
        }
    };

    // Ausschalten aller Outputs
    const allOutputsOff = async () => {
        try {
            const response = await axios.post('/api/motor/all-off');
            console.log(response.data);
        } catch (error) {
            console.error('Fehler beim Ausschalten aller Outputs:', error);
        }
    };

    const getIndicatorStyle = (state) => ({
        width: '20px',
        height: '20px',
        borderRadius: '50%',
        backgroundColor: state ? 'green' : 'red',
        display: 'inline-block',
        marginRight: '10px'
    });

    const getRowStyle = (group) => {
        switch (group) {
            case "dryve":
                return { backgroundColor: '#cce5ff' };
            case "schrauber":
                return { backgroundColor: '#ccffcc' };
            case "druckluft":
                return { backgroundColor: '#ffe6cc' };
            default:
                return { backgroundColor: '#ffffff' };
        }
    };

    const containerStyle = {
        display: 'flex',
        justifyContent: 'space-between',
        gap: '20px',
        marginTop: '20px'
    };

    const tableWrapperStyle = {
        flex: '1',
        minWidth: '0'
    };

    return (
        <div>
            <h2>Pin-Layout</h2>
            <div style={containerStyle}>
                <div style={tableWrapperStyle}>
                    <h3>Ausgänge (Outputs)</h3>
                    <table border="1" cellPadding="5" style={{ width: '100%' }}>
                        <thead>
                            <tr>
                                <th>Pin Nr.</th>
                                <th>Funktion</th>
                                <th>Status</th>
                                <th>Toggle</th>
                            </tr>
                        </thead>
                        <tbody>
                            {outputsData.map((output) => (
                                <tr key={output.id} style={getRowStyle(output.group)}>
                                    <td>{output.id}</td>
                                    <td>{output.label}</td>
                                    <td>
                                        <span style={getIndicatorStyle(outputs[output.id])}></span>
                                        {outputs[output.id] ? 'An' : 'Aus'}
                                    </td>
                                    <td>
                                        <button onClick={() => toggleOutput(output.id)}>Toggle</button>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                    <button onClick={toggleAllOutputs} style={{ marginRight: '10px' }}>
                        Alles umschalten
                    </button>
                    <button onClick={allOutputsOff}>
                        Alle aus
                    </button>
                </div>
                <div style={tableWrapperStyle}>
                    <h3>Eingänge (Inputs)</h3>
                    <table border="1" cellPadding="5" style={{ width: '100%' }}>
                        <thead>
                            <tr>
                                <th>Pin Nr.</th>
                                <th>Funktion</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            {inputsData.map((input) => (
                                <tr key={input.id} style={getRowStyle(input.group)}>
                                    <td>{input.id}</td>
                                    <td>{input.label}</td>
                                    <td>
                                        <span style={getIndicatorStyle(inputs[input.id])}></span>
                                        {inputs[input.id] ? 'An' : 'Aus'}
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
};

export default PinLayout;
