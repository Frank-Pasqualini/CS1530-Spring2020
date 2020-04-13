import React, { Component } from 'react';
import styled from 'styled-components';
import Playlist from './Playlist';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const Lists = styled.div`
  font-size: 50px;
  color: white;
  align-self: center;
  margin: 30px 0 0;
`;

class SelectPlist extends Component {
  constructor(props) {
    super()
    this.plist = props.playList.map((item, key) =>
      <Playlist id={item.id} coverArt={item.img_url} name={item.name} key={key}/>
    );
  }
  render() {
    return (
      <Container>
        <Lists>Your Playlists</Lists>
        {
          this.plist
        }
      </Container>
    )
  }
}
export default SelectPlist; 