import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import styled from 'styled-components'

const Title = styled.h1`
  font-size: 1.5em;
  text-align: center;
  color: palevioletred;
`;

class App extends Component {
  render() {
    return (
      <div className="App">
        <header className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <p>
            Jambox y
          </p>
          <Title>
            Wooooo
          </Title>
        </header>
      </div>
    );
  }
}

export default App;
