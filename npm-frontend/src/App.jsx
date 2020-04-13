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
import NewEvent from './CreateNewEvent';
import ShowCode from './ShowCode';
import GuestInterface from './GuestInterface';
import HostInterface from './HostInterface';
import JoinCodeError from './JoinCodeError';
import SelectPlist from './SelectPlist';

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
`;
const playList2 = [
  {
    "name": "Rock",
    "id": "1",
    "web_url": "http://aln3.albumlinernotes.com/Vice_Verses.html",
    "img_url": "http://aln3.albumlinernotes.com/images/a4012b5a07e2dcfb1ab219de77c6c019.jpg",
  },
  {
    "name": "POP",
    "id": "2",
    "web_url": "https://musicbrainz.org/release-group/3843ba6a-0b1e-48c0-bc86-2f64e6d9525c",
    "img_url": "https://coverartarchive.org/release-group/3843ba6a-0b1e-48c0-bc86-2f64e6d9525c/front.jpg",
  },
  {
    "name": "Metal",
    "id": "3",
    "web_url": "https://musicbrainz.org/release-group/55d8ab42-990b-4c94-93c8-9ca7927b5fc0",
    "img_url": "https://coverartarchive.org/release-group/55d8ab42-990b-4c94-93c8-9ca7927b5fc0/front.jpg",
  },
  {
    "name": "Dubstep",
    "id": "4",
    "web_url": "https://musicbrainz.org/release-group/c2142471-c85b-4433-849c-839aafc8e3a4",
    "img_url": "https://coverartarchive.org/release-group/c2142471-c85b-4433-849c-839aafc8e3a4/front.jpg",
  },
  {
    "name": "Classical",
    "id": "5",
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
    "name": "Random",
    "id": "5",
    "web_url": "http://aln3.albumlinernotes.com/Rogers_-_21_Number_Ones.html",
    "img_url": "http://aln3.albumlinernotes.com/images/bb9d7c262a03af6b59968e163ee3cf2d_d7zd_5c14.png",
  },
]
const songList = [
  {
  "title": "1904",
  "artist": "The Tallest Man on Earth",
  "year": "2012",
  "web_url": "http://www.songnotes.cc/songs/78-the-tallest-man-on-earth-1904",
  "img_url": "http://www.songnotes.cc/images/artists/TheTallestManOnEarth.jpg",
  "voteCount": 0
  },
  {
  "title": "#40",
  "artist": "Dave Matthews",
  "year": "1999",
  "web_url": "http://www.songnotes.cc/songs/119-dave-matthews-40",
  "img_url": "http://www.songnotes.cc/images/artists/DaveMatthews.jpg",
  "voteCount": 0
  },
  {
  "title": "40oz to Freedom",
  "artist": "Sublime",
  "year": "1996",
  "web_url": "http://www.songnotes.cc/songs/45-sublime-40oz-to-freedom",
  "img_url": "http://www.songnotes.cc/images/artists/Sublime.jpg",
  "voteCount": 0
  },
  {
  "title": "#41",
  "artist": "Dave Matthews",
  "year": "1996",
  "web_url": "http://www.songnotes.cc/songs/46-dave-matthews-band-41",
  "img_url": "http://www.songnotes.cc/images/artists/DaveMatthews.jpg",
  "voteCount": 0
  },
  {
  "title": "American Girl",
  "artist": "Tom Petty",
  "year": "1977",
  "web_url": "http://www.songnotes.cc/songs/86-tom-petty-american-girl",
  "img_url": "http://www.songnotes.cc/images/artists/TomPetty.jpg",
  "voteCount": 0
  },
  {
  "title": "American Music",
  "artist": "Violent Femmes",
  "year": "1991",
  "web_url": "http://www.songnotes.cc/songs/123-violent-femmes-american-music",
  "img_url": "http://www.songnotes.cc/images/artists/ViolentFemmes.jpg",
  "voteCount": 0
  },
  {
  "title": "American Pie",
  "artist": "Don McLean",
  "year": "1972",
  "web_url": "http://www.songnotes.cc/songs/132-don-mclean-american-pie",
  "img_url": "http://www.songnotes.cc/images/artists/DonMcLean.jpg",
  "voteCount": 0
  },
  {
  "title": "And it Stoned Me",
  "artist": "Van Morrison",
  "year": "1970",
  "web_url": "http://www.songnotes.cc/songs/27-van-morrison-and-it-stoned-me",
  "img_url": "http://www.songnotes.cc/images/artists/VanMorrison.jpg",
  "voteCount": 0
  },
  {
  "title": "A Sailor's Christmas",
  "artist": "Jimmy Buffett",
  "year": "1996",
  "web_url": "http://www.songnotes.cc/songs/11-jimmy-buffett-a-sailors-christmas",
  "img_url": "http://www.songnotes.cc/images/artists/JimmyBuffett.jpg",
  "voteCount": 0
  },
  {
  "title": "Badfish",
  "artist": "Sublime",
  "year": "1996",
  "web_url": "http://www.songnotes.cc/songs/21-sublime-badfish",
  "img_url": "http://www.songnotes.cc/images/artists/Sublime.jpg",
  "voteCount": 0
  },
  {
  "title": "Banana Pancakes",
  "artist": "Jack Johnson",
  "year": "2005",
  "web_url": "http://www.songnotes.cc/songs/102-jack-johnson-banana-pancakes",
  "img_url": "http://www.songnotes.cc/images/artists/JackJohnson.jpg",
  "voteCount": 0
  },
  {
  "title": "Barefoot Children",
  "artist": "Jimmy Buffett",
  "year": "1995",
  "web_url": "http://www.songnotes.cc/songs/9-jimmy-buffett-barefoot-children",
  "img_url": "http://www.songnotes.cc/images/artists/JimmyBuffett.jpg",
  "voteCount": 0
  },
  {
  "title": "Big Parade",
  "artist": "The Lumineers",
  "year": "2012",
  "web_url": "http://www.songnotes.cc/songs/63-the-lumineers-big-parade",
  "img_url": "http://www.songnotes.cc/images/artists/TheLumineers.jpg",
  "voteCount": 0
  },
  {
  "title": "Brown Eyed Girl",
  "artist": "Van Morrison",
  "year": "1967",
  "web_url": "http://www.songnotes.cc/songs/69-van-morrison-brown-eyed-girl",
  "img_url": "http://www.songnotes.cc/images/artists/VanMorrison.jpg",
  "voteCount": 0
  },
  {
  "title": "Cape Canaveral",
  "artist": "Conor Oberst",
  "year": "2008",
  "web_url": "http://www.songnotes.cc/songs/75-conor-oberst-cape-canaveral",
  "img_url": "http://www.songnotes.cc/images/artists/ConorOberst.jpg",
  "voteCount": 0
  },
  {
  "title": "Carry On",
  "artist": "fun.",
  "year": "2012",
  "web_url": "http://www.songnotes.cc/songs/122-fun-carry-on",
  "img_url": "http://www.songnotes.cc/images/artists/Fun.jpg",
  "voteCount": 0
  },
  {
  "title": "Catch the Wind",
  "artist": "Donovan",
  "year": "1965",
  "web_url": "http://www.songnotes.cc/songs/131-donovan-catch-the-wind",
  "img_url": "http://www.songnotes.cc/images/artists/Donovan.jpg",
  "voteCount": 0
  },
  {
  "title": "Cat's in the Cradle",
  "artist": "Harry Chapin",
  "year": "1974",
  "web_url": "http://www.songnotes.cc/songs/47-harry-chapin-cats-in-the-cradle",
  "img_url": "http://www.songnotes.cc/images/artists/HarryChapin.jpg",
  "voteCount": 0
  },
  {
  "title": "Changes in Latitudes, Changes in Attitudes",
  "artist": "Jimmy Buffett",
  "year": "1977",
  "web_url": "http://www.songnotes.cc/songs/38-jimmy-buffett-changes-in-latitudes-changes-in-attitudes",
  "img_url": "http://www.songnotes.cc/images/artists/JimmyBuffett.jpg",
  "voteCount": 0
  },
  {
  "title": "Classy Girls",
  "artist": "The Lumineers",
  "year": "2012",
  "web_url": "http://www.songnotes.cc/songs/55-the-lumineers-classy-girls",
  "img_url": "http://www.songnotes.cc/images/artists/TheLumineers.jpg",
  "voteCount": 0
  }
];

class App extends Component {
  constructor(){
    super();
    const params = this.getHashParams();
    console.log(params);
    
    // Get event code out of storage if there is one
    const eventCode = JSON.parse(localStorage.getItem('eventCode'));
    const currName = JSON.parse(localStorage.getItem('currName'));

    this.state = {
      eventList: [],
      thisEvent: eventCode || '',
      validCode: false,
      codeInput: 0,
      name: currName || ''
    }
  }

  componentDidMount() {
    // This syncs the event list with firebase. Any new data added to it will automatically update to firebase
    base.syncState(`eventList`, {
      context: this,
      state: 'eventList',
      asArray: true
    });
  }

  // This is run from CreateNewEvent and adds a new event to the event list
  addEvent = () => {
    const eventList = [...this.state.eventList];
    // Create a random 4 digit number for the event code
    const thisEvent = Math.floor(1000 + Math.random() * 9000);

    // Add the new event code to the event list along with the host name
    eventList.push({
      eventCode: thisEvent,
      // TODO: Update hostname with their spotify name
      hostName: `Host ${thisEvent}`
    });

    // Update the global event list with the new event code
    this.setState({ eventList });
    // Update state with the current event code
    this.setState({ thisEvent });
    // Update the name in state with the host's name
    this.setState({name: `Host ${thisEvent}` })

    // Store the event code in local storage so you won't lose it when you make changes to files
    localStorage.setItem('eventCode', JSON.stringify(thisEvent))
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
        this.setState({ thisEvent: codeInput})

        // Store the event code in local storage so it stays when you make changes to files 
        localStorage.setItem('eventCode', JSON.stringify(this.state.thisEvent))
      }
    })
  }

  // This is run from JoinEventUsername and adds the guest username to the guestList
  // TODO (maybe): update the connection with firebase so we don't have to loop through all the events all the time 
  addGuest = () => {
    const eventList = [...this.state.eventList];
    const thisEvent = this.state.thisEvent

    // Loop through each event in the event list
    eventList.forEach((e) => {
      // Check if the event code equals the current event
      if(e.eventCode === thisEvent) {
        // If guestList hasn't been created, create one
        if(e.guestList === undefined) {e.guestList = [];}
        // Add the name to the guestList
        e.guestList.push(this.state.name);
        // Update the eventList
        this.setState({ eventList });


      }
    })
  }

  // This is run when "Join Event" or "Create Event" are clicked on the homepage
  emptyLocalStorage = () => {
    // Clear out any event code from local storage
    localStorage.clear();
  }

  getHashParams() {
    let hashParams = {};
    let e, r = /([^&;=]+)=?([^&;]*)/g,
        q = window.location.hash.substring(1);
    e = r.exec(q)
    while (e) {
       hashParams[e[1]] = decodeURIComponent(e[2]);
       e = r.exec(q);
    }
    return hashParams;
  }

  render() {
    return (
      <Router>
          <TopBar>
            <Link to="/" style={{ textDecoration: 'none' }}>
              <Logo navBar={true}/>
            </Link>
          </TopBar>
          <Background>
            <Switch>
              <Route exact path="/"> <Homepage emptyLocalStorage={this.emptyLocalStorage} /> </Route>
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
                <NewEvent addEvent={this.addEvent} />
              </Route>
              <Route path="/show-code">
                <ShowCode thisEvent={this.state.thisEvent} />
              </Route>
              <Route path="/guest"> <GuestInterface songList={songList}/> </Route>
              <Route path="/host"> <HostInterface songList={songList}/> </Route>
              <Route path="/invalid-code"> 
                <JoinCodeError />
              </Route>
              <Route path="/choose-playlist-d"> <SelectPlist playList={playList2}/> </Route>
              <Route path="/choose-playlist"> <SelectPlist playList={playList}/> </Route>
            </Switch>
          </Background>
      </Router>
    );
  }
}

export default App;
