import React, { Component } from 'react';
import styled from 'styled-components';
import { Link } from 'react-router-dom';

const PlistButton = styled.button`
  height: 100px;
  width: 500px;
  background: #272726;
  margin: 6px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-radius: 5px;
  border: #7a7b80;
  border-style: solid;
  border-radius: 5px;
  border-width: 1px;
  color: white;
  cursor: pointer;
`;

const CoverArt = styled.img`
  height: 75px;
  margin: 0 15px;
`;

const PlistName = styled.div`
  font-size: 25px;
`;

const PlistInfo = styled.div`
  width: 450px;
`;

class Playlist extends Component {
  constructor(props) {
    super();
  }

  render() {
    return (
      <Link to="/show-code" style={{ textDecoration: 'none' }}>
        <PlistButton>
          <CoverArt src={this.props.coverArt} />
          <PlistInfo>
            <PlistName>{this.props.name}</PlistName>
          </PlistInfo>
        </PlistButton>
      </Link>
    )
  }
}
  
  export default Playlist; 