import { Options } from '@angular-slider/ngx-slider';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';

interface Category {
  value: string;
  viewValue: string;
  image: string;
}
@Component({
  selector: 'app-size',
  templateUrl: './size.component.html',
  styleUrls: ['./size.component.css']
})
export class SizeComponent {
  image: string = '';
  nameProduct="Vui lòng chọn loại sản phẩm..."
  check = new FormControl();
  categories: Category[] = [
    { image: "https://mcdn.coolmate.me/uploads/November2020/31.jpg", value: "2", viewValue: 'Áo dài tay' },
    { image: "https://mcdn.coolmate.me/image/May2022/mceclip0_5.png", value: "ao-oversize", viewValue: 'Áo oversize' },
    { image: "https://mcdn.coolmate.me/uploads/March2022/TEE_EXCOOL_(2)_(1).png", value: "excool-tee", viewValue: 'Excool Tee' },
    { image: "https://mcdn.coolmate.me/uploads/December2021/POLO2x.png", value: "polo", viewValue: '  Áo Polo ' },
    { image: "https://mcdn.coolmate.me/uploads/December2021/ULTRA_LIGHT2x_(1).png", value: "jacket-ultra-light", viewValue: ' Áo Jacket ultra light' },
    { image: "https://mcdn.coolmate.me/uploads/December2021/AO_TRE_EM2_copy-0_(1)_(1).png", value: "jacket-kids", viewValue: 'Áo New normal jacket kids ' },
    { image: "https://mcdn.coolmate.me/uploads/December2021/NNJ2x.png", value: "jacket", viewValue: ' Áo New normal jacket' },
    { image: "https://mcdn.coolmate.me/uploads/December2021/SO_MI_DAI2x.png", value: "shirt-long", viewValue: ' Áo sơ mi dài tay ' },
    { image: "https://mcdn.coolmate.me/uploads/December2021/SO_MI_NGAN2x.png", value: "shirt-short", viewValue: ' Áo sơ mi ngắn tay ' },
    { image: "https://mcdn.coolmate.me/uploads/December2021/MAXCOOL_(1).png", value: "maxcool", viewValue: ' Áo thể thao Cool247' },
    { image: "https://mcdn.coolmate.me/uploads/December2021/AO_THUN2x.png", value: "tshirt-compact", viewValue: ' Áo thun cổ tròn chất liệu Cotton Compact ' },
    { image: "https://mcdn.coolmate.me/uploads/December2021/POLO_THE_THAO2x.png", value: "polo-sport", viewValue: ' Áo Polo Thể thao ' },
    { image: "https://mcdn.coolmate.me/uploads/December2021/TANK_TOP2x.png", value: "tank-top", viewValue: ' Áo Tank Top ' },
    { image: "https://mcdn.coolmate.me/uploads/December2021/JEANS2x.png", value: "jeans", viewValue: ' Quần Jeans ' },
    { image: "https://mcdn.coolmate.me/uploads/March2022/REGULAR_JEANS_(1).png", value: "jeans-regular", viewValue: ' Jeans Regular ' },
    { image: "https://mcdn.coolmate.me/uploads/January2022/2411_(1)_(1).png", value: "jeans-v2", viewValue: ' Quần Jeans V2 ' },
    { image: "https://mcdn.coolmate.me/uploads/December2021/SORONA_DAI2x_(1)_(1).png", value: "jogger-pants", viewValue: ' Quần dài Daily Pants ' },
    { image: "https://mcdn.coolmate.me/uploads/December2021/JOGGER_THE_THAO2x_(1).png", value: "jogger-graphene", viewValue: ' Jogger Graphene ' },
    { image: "https://mcdn.coolmate.me/uploads/December2021/JOGGER_THE_THAO2x_(1).png", value: "jogger-co-dan", viewValue: ' Quần dài Jogger Co dãn ' },
    { image: "https://mcdn.coolmate.me/uploads/December2021/jogger2x.png", value: "jogger-swearpants", viewValue: ' Quần dài Jogger Sweatpants ' },
    { image: "https://mcdn.coolmate.me/uploads/December2021/ULTRA_SHORT2x.png", value: "ultra-short-sport", viewValue: ' Quần thể thao Ultra Short ' },
    { image: "https://mcdn.coolmate.me/uploads/December2021/MAX_ULTRA2x.png", value: "ultra-long-sport", viewValue: ' Quần Max Ultra dáng dài' },
    { image: "https://mcdn.coolmate.me/uploads/December2021/KAKI2x.png", value: "kaki", viewValue: ' Quần Kaki ' },
    { image: "https://mcdn.coolmate.me/uploads/December2021/EZ_ACTIV2x.png", value: "easy-active", viewValue: ' Quần Short ' },
    { image: "https://mcdn.coolmate.me/uploads/December2021/EZ_ACTIV2x.png", value: "easy-active-2", viewValue: ' Easy Active 2 ' },
    { image: "https://mcdn.coolmate.me/uploads/December2021/TRUNK2x.png", value: "trunk", viewValue: ' Underwear trunk ' },
    { image: "https://mcdn.coolmate.me/uploads/December2021/BOXER2x.png", value: "brief-boxer", viewValue: ' Underwear brief boxer' },
    { image: "https://mcdn.coolmate.me/uploads/December2021/BRIEF2x.png", value: "brief", viewValue: ' Underwear brief tam giác ' },
    { image: "https://mcdn.coolmate.me/uploads/December2021/3_LO_BASIC2x_(1).png", value: "ao-ba-lo", viewValue: ' Áo ba lỗ ' },
    { image: "https://mcdn.coolmate.me/uploads/December2021/3_LO_BASIC2x_(1).png", value: "ao-ba-lo-cotton", viewValue: ' Áo ba lỗ cotton' },
    { image: "https://mcdn.coolmate.me/uploads/December2021/KAKI_EXCOOL_RS.png", value: "kaki_excool", viewValue: ' Quần dài Kaki Excool co giãn ' },
    { image: "https://mcdn.coolmate.me/uploads/December2021/SHORT_9in2x.png", value: "short-form-lining", viewValue: ' Short thể thao 9inch ' },
    { image: "https://mcdn.coolmate.me/uploads/January2022/QUAN_NGU_(2).png", value: "quan-mac-nha", viewValue: ' Quần mặc nhà ' },
    { image: "https://mcdn.coolmate.me/image/June2022/mceclip0_92.png", value: "quan-short-5-recycle", viewValue: ' Quần short 5 & quot; recycle ' },
  ];

  uploadImage(item:any){
    console.log(item);
    this.image = item.image;
    this.nameProduct=item.viewValue;
  }
}
