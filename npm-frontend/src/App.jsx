import React, { Component } from 'react';
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link,
  Redirect
} from "react-router-dom";
import base from './base.js';
import styled from 'styled-components';
import Logo from './Logo';
import Homepage from './Homepage';
import JoinEventCode from './JoinEventCode';
import JoinEventUsername from './JoinEventUsername';
import ErrorPage from './ErrorPage';
import CreateNewEvent from './CreateNewEvent';
import ShowCode from './ShowCode';
import GuestInterface from './GuestInterface';
import HostInterface from './HostInterface';
import JoinCodeError from './JoinCodeError';
import SelectPlist from './SelectPlist';
import EndEvent from './EndEvent';
import DisneyArt from './Disney.jpeg';

const Background = styled.div`
  background-color: #272727;
  min-height: 100vh;
  width: 100%;
  display: flex;
  justify-content: center;
`;

const TopBar = styled.div`
  height: 60px;
  background-color: #EBEBEB;
  display: flex;
  justify-content: space-between;
`;

const playListd = [
  {
    "name": "Rock",
    "id": "6",
    "web_url": "http://aln3.albumlinernotes.com/Vice_Verses.html",
    "img_url": "http://aln3.albumlinernotes.com/images/a4012b5a07e2dcfb1ab219de77c6c019.jpg",
  },
  {
    "name": "POP",
    "id": "7",
    "web_url": "https://musicbrainz.org/release-group/3843ba6a-0b1e-48c0-bc86-2f64e6d9525c",
    "img_url": "https://coverartarchive.org/release-group/3843ba6a-0b1e-48c0-bc86-2f64e6d9525c/front.jpg",
  },
  {
    "name": "Metal",
    "id": "8",
    "web_url": "https://musicbrainz.org/release-group/55d8ab42-990b-4c94-93c8-9ca7927b5fc0",
    "img_url": "https://coverartarchive.org/release-group/55d8ab42-990b-4c94-93c8-9ca7927b5fc0/front.jpg",
  },
  {
    "name": "Dubstep",
    "id": "9",
    "web_url": "https://musicbrainz.org/release-group/c2142471-c85b-4433-849c-839aafc8e3a4",
    "img_url": "https://coverartarchive.org/release-group/c2142471-c85b-4433-849c-839aafc8e3a4/front.jpg",
  },
  {
    "name": "Classical",
    "id": "10",
    "web_url": "http://aln3.albumlinernotes.com/Rogers_-_21_Number_Ones.html",
    "img_url": "https://coverartarchive.org/release-group/0e587fbc-3d59-4f8b-b09d-dd2d57bf50bd/front.jpg",
  },
]
const playList = [
  {
    "name": "Oldies",
    "id": "1",
    "web_url": "http://albumlinernotes.com/Good_Feeling__1997_.html",
    "img_url": "http://albumlinernotes.com/images/2a102b85cb897c8823a86e045653bffd_kxkd.png",
  },
  {
    "name": "DMT Playlist",
    "id": "2",
    "web_url": "http://albumlinernotes.com/Band_On_The_Run__1973_.html",
    "img_url": "http://albumlinernotes.com/images/9376ca665018b046b3ec8f5c6b65f59d_r3i3_dm8p.png",
  },
  {
    "name": "TGIF",
    "id": "3",
    "web_url": "http://aln3.albumlinernotes.com/21.html",
    "img_url": "http://aln3.albumlinernotes.com/images/1f3330045cfe67c27739519980e23444_9ji6.png",
  },
  {
    "name": "Movie OSTs",
    "id": "4",
    "web_url": "http://aln3.albumlinernotes.com/Charlotte_s_Web.html",
    "img_url": "http://aln3.albumlinernotes.com/images/1f63da331aa3e8ee5d61a093b835fe24_w8bk.png",
  },
  {
    "name": "Disney",
    "id": "5",
    "web_url": "http://aln3.albumlinernotes.com/Rogers_-_21_Number_Ones.html",
    "img_url": `${DisneyArt}`,
  },
]

class App extends Component {
  constructor(){
    super();

    // Get event code out of storage if there is one
    const eventCode = JSON.parse(localStorage.getItem('eventCode'));
    const currName = JSON.parse(localStorage.getItem('currName'));
    const accessToken = JSON.parse(localStorage.getItem('accessToken'));
    const songData = JSON.parse(localStorage.getItem('songData'));
    const hostId = JSON.parse(localStorage.getItem('hostId'));

    this.state = {
      eventList: [],
      currEventCode: eventCode || '',
      validCode: false,
      codeInput: 0,
      name: currName || '',
      accessToken: accessToken || '', 
      songData: songData || '',
      host: false,
      hostId: hostId || '',
      accountType: ''
    }
  }

  componentDidMount() {
    // This syncs the event list with firebase. Any new data added to it will automatically update to firebase
    base.syncState(`eventList`, {
      context: this,
      state: 'eventList',
      asArray: true
    });
    base.syncState(`songData`, {
      context: this,
      state: 'songData'
    });
  }

  updateHostId = (id, type) => {
    let pieces = id.split("user:");
    console.log(pieces);
    this.setState({ hostId: pieces[1], accountType: type });
    localStorage.setItem('hostId', JSON.stringify(pieces[1]));
  }

  updateEventCode = (code) => {
    this.setState({ currEventCode: code });
  }

  // This is run from CreateNewEvent and adds a new event to the event list
  addEvent = (code) => {
    const eventList = [...this.state.eventList];

    // Add the new event code to the event list along with the host name
    eventList.push({
      eventCode: code,
      // TODO: Update hostname with their spotify name
      hostName: `Host ${code}`
    });

    // Update the global event list with the new event code
    this.setState({ eventList });
    // Update state with the current event code
    this.setState({ currEventCode: code });
    // Update the name in state with the host's name
    this.setState({name: `Host ${code}` });
    this.setState({ host: true });

    // Store the event code in local storage so you won't lose it when you make changes to files
    localStorage.setItem('eventCode', JSON.stringify(code));
  }

  // This is run from JoinEventCode and updates state with the input from the textbox
  updateCodeInput = (e) => {
    this.setState({
       codeInput: parseFloat(e.target.value)
    })
  }

  // This is run from JoinEventUsername and updates state with the input from the textbox
  updateNameInput = (e) => {
    this.setState({
       name: e.target.value
    })
    // Set guest name in local storage so it stays even when page is refreshed
    localStorage.setItem('currName', JSON.stringify(e.target.value));
  }

  // This is run from JoinEventCode and checks if the submitted event code is valid 
  checkCode = () => {
    const codeInput = this.state.codeInput

    // Loop through each event in the event list
    this.state.eventList.forEach((e) => {
      // Check if the event code equals the submitted value
      if(e.eventCode === codeInput) {
        // Update the validCode status to true so that the correct page renders (the username page can now render)
        this.setState({ validCode: true })
        // Update the current event code 
        this.setState({ currEventCode: codeInput})

        // Store the event code in local storage so it stays when you make changes to files 
        localStorage.setItem('eventCode', JSON.stringify(this.state.currEventCode))
      }
    })
  }

  // This is run from JoinEventUsername and adds the guest username to the guestList
  // TODO (maybe): update the connection with firebase so we don't have to loop through all the events all the time 
  addGuest = () => {
    const eventList = [...this.state.eventList];
    const currEventCode = this.state.currEventCode    

    // Loop through each event in the event list
    eventList.forEach((e) => {
      // Check if the event code equals the current event
      if(e.eventCode === currEventCode) {
        // If guestList hasn't been created, create one
        if(e.guestList === undefined) {e.guestList = [];}
        // Add the name to the guestList
        e.guestList.push(this.state.name);
        // Update the eventList
        this.setState({ eventList });

        console.log(this.state.name);
      }
    })
    // Update the backend with the new user
    fetch(`http://localhost:8080/api/add_user?eventCode=${this.state.currEventCode}&userId=${this.state.name}`);
  }

  // This is run when "Join Event" or "Create Event" are clicked on the homepage
  emptyLocalStorage = () => {
    console.log('emptying storage')
    // Clear out any event code from local storage
    localStorage.clear();
    this.setState({ host: false });
  }

  updateToken = (token) => {
    // Store the access token
    localStorage.setItem('accessToken', JSON.stringify(token));
    this.setState({ accessToken: token });
  }

  updateSongs = (data) => {
    this.setState({ songData: data })
    localStorage.setItem('songData', JSON.stringify(data));
  }

  endEvent = () => {
    // Change the hostid if you want to run this with your own spotify account
    fetch(`http://localhost:8080/api/end_event?eventCode=${this.state.currEventCode}&hostId=${this.state.hostId}&accessToken=${this.state.accessToken}`);

    this.emptyLocalStorage();
  }

  logout = () => {
    fetch(`http://localhost:8080/api/disconnect?eventCode=${this.state.currEventCode}&userId=${this.state.name}`)
  }

  render() {
    return (
      <Router>
          <TopBar>
            <Link to="/" onClick={this.emptyLocalStorage} style={{ textDecoration: 'none' }}>
              <Logo navBar={true} />
            </Link>
          </TopBar>
          <Background>
            <Switch>
              <Route exact path="/"> <Homepage /> </Route>
              <Route path="/join-code"> 
                <JoinEventCode updateCodeInput={this.updateCodeInput} checkCode={this.checkCode} />
              </Route>
              <Route path="/join-username">
                {!this.state.validCode ? <Redirect to="/invalid-code" /> : <JoinEventUsername addGuest={this.addGuest} updateNameInput={this.updateNameInput} />}
              </Route>
              <Route path="/premium-error">
                <ErrorPage />
              </Route>
              <Route path="/create-new-event">
                <CreateNewEvent updateToken={this.updateToken} updateHostId={this.updateHostId} />
              </Route>
              <Route path="/show-code">
                <ShowCode 
                  currEventCode={this.state.currEventCode} 
                  accessToken={this.state.accessToken} 
                  addEvent={this.addEvent} 
                  updateEventCode={this.updateEventCode} 
                  hostId={this.state.hostId}
                />
              </Route>
              <Route path="/guest"> 
                <GuestInterface 
                  songData={this.state.songData} 
                  currEventCode={this.state.currEventCode} 
                  updateSongs={this.updateSongs} 
                  userID={this.state.name} 
                  logout={this.logout}
                  name={this.state.name}
                /> 
              </Route>
              <Route path="/host">
                <HostInterface 
                  accessToken={this.state.accessToken} 
                  currEventCode={this.state.currEventCode} 
                  updateSongs={this.updateSongs} 
                  songData={this.state.songData} 
                  endEvent={this.endEvent}
                  hostId={this.state.hostId}
                  /> 
                </Route>
              <Route path="/invalid-code"> 
                <JoinCodeError />
              </Route>
              <Route path="/choose-playlist-d"> <SelectPlist playList={playListd}/> </Route>
              <Route path="/choose-playlist"> <SelectPlist playList={playList}/> </Route>
              <Route path="/end-event"><EndEvent endEvent={this.endEvent} /></Route>
            </Switch>
          </Background>
      </Router>
    );
  }
}

export default App;
