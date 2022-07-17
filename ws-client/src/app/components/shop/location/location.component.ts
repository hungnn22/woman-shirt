import { Location } from './../../../models/location';
import { LocationService } from './../../../services/location.service';
import { Component, OnInit } from '@angular/core';
import { DomSanitizer, SafeResourceUrl, SafeUrl } from '@angular/platform-browser';


@Component({
  selector: 'app-location',
  templateUrl: './location.component.html',
  styleUrls: ['./location.component.css']
})


export class LocationComponent implements OnInit {

  locations: any[] = [];
  count:number=0;
  constructor(private locationService: LocationService, private sanitizer: DomSanitizer) {
  }

  ngOnInit(): void {
    this.getLocation();
  }
  trustedDashboardUrl: SafeUrl = "";
  getLocation() {
    this.locationService.getListLocation().subscribe((data: any) => {
      this.locations = data.data.data;
      this.count =this.locations.length;
      console.log(this.locations);

    });
  }

  uploadLocation(item: any) {
    console.log("lấy được: " + item.addressLink);
    // this.linkLocation = item.addressLink;
    this.trustedDashboardUrl = this.sanitizer.bypassSecurityTrustResourceUrl(item.addressLink);
  }
}
