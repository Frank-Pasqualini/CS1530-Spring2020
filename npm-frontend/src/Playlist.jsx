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
  margin: 15px;
`;

const PlayInfo = styled.div`
  width: 450px;
`;

const PlistName = styled.div`
  font-size: 25px;
`;

const PlistID = styled.div`
  color: #aaa;
  font-size: 18px;
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
        <Link to="/show-code">
      <PlistButton>
        <CoverArt src={this.props.album} />
        <PlistInfo>
          <PlistName>{this.props.name}</PlistName>
          <PlistID>{this.props.id}</PlistID>
        </PlistInfo>
      </PlistButton>
      </Link>
    )
  }
}
  
  export default Playlist; 