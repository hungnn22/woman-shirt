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
    this.trustedDashboardUrl = this.sanitizer.bypassSecurityTrustResourceUrl("https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3723.8639810443356!2d105.74459841485445!3d21.038127785993236!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x313454b991d80fd5%3A0x53cefc99d6b0bf6f!2zVHLGsOG7nW5nIENhbyDEkeG6s25nIEZQVCBQb2x5dGVjaG5pYw!5e0!3m2!1svi!2s!4v1657889087360!5m2!1svi!2s");


    });
  }

  uploadLocation(item: any) {
    console.log("lấy được: " + item.addressLink);
    // this.linkLocation = item.addressLink;
    this.trustedDashboardUrl = this.sanitizer.bypassSecurityTrustResourceUrl(item.addressLink);
  }
}
