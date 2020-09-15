select model,speed,hd from PC where price<500

select maker from product where type='Printer' group by maker

select model,ram,screen from laptop where price>1000

select * from printer where color='y'

select model,speed,hd from pc where (cd='12x' or cd='24x') and price<600

select distinct p.maker, l.speed from laptop l join product p on p.model = l.model where l.hd >=10

select distinct product.model, pc.price from product join pc on product.model=pc.model where maker='B' union select distinct product.model, laptop.price from product join laptop on product.model=laptop.model where maker='B' union select distinct product.model,printer.price from product join printer on product.model=printer.model where maker='B'

select distinct maker from product where type='pc' except select distinct maker from product where type='laptop'

select distinct product.maker from pc join product on pc.model=product.model where pc.speed>='450'

select model,price from printer where price=(select max(price) from printer)

select avg(speed) from pc

select avg(speed) from laptop where price>1000

select avg(speed) from product join pc on product.model=pc.model where product.maker='A'

select classes.class, ships.name, classes.country from classes join ships on ships.class=classes.class where classes.numGuns>=10

select hd from pc group by(hd) having count(model)>1

select distinct pc1.model,pc2.model,pc1.speed,pc1.ram from pc pc1, pc pc2 where pc1.speed=pc2.speed and pc1.ram=pc2.ram and pc1.model>pc2.model

select distinct product.type,laptop.model,laptop.speed from product join laptop on product.model=laptop.model where laptop.speed<(select min(speed) from pc)

select distinct product.maker,printer.price from product join printer on product.model=printer.model where printer.color='y' and printer.price=(select min(price) from printer where printer.color='y')

select product.maker, avg(screen) from laptop join product on laptop.model=product.model group by product.maker

select maker,count(model) from product where type='pc' group by product.maker having count(model)>=3

select product.maker, max(pc.price) from product,pc where product.model=pc.model group by product.maker

select speed,avg(price) from pc where speed>600 group by speed

select distinct maker from pc join product on pc.model=product.model where pc.speed>=750 and maker in (select distinct maker from laptop join product on laptop.model=product.model where laptop.speed>=750)

select model from (select model, price from pc union select model, price from laptop union select model, price from printer) t1 where price = (select max(price) from (select price from pc union select price from laptop union select price from printer) t2)

select distinct maker from product where model in (select model from pc where ram = (select min(ram) from pc) and speed = (select max(speed) from pc where ram = (select min(ram) from pc))) and maker in (select maker from product where type='printer')

select sum(s.price)/sum(s.kol) from (select price,1 as kol from pc, product where pc.model=product.model and product.maker='a' union all select price,1 as kol from laptop,product where laptop.model=product.model and product.maker='a') as s

select product.maker, avg(pc.hd) from pc, product where product.model = pc.model and product.maker in (select distinct maker from product where product.type = 'printer') group by maker

select count(maker) from product where maker in (select maker from product group by maker having count(model) = 1)

select t1.point, t1.date, inc, out from income_o t1 left join outcome_o t2 on t1.point = t2.point and t1.date = t2.date union select t2.point, t2.date, inc, out from income_o t1 right join outcome_o t2 on t1.point = t2.point and t1.date = t2.date

select point, date, sum(sum_out), sum(sum_inc) from ( select point, date, sum(inc) as sum_inc, null as sum_out from income group by point, date union select point, date, null as sum_inc, sum(out) as sum_out from outcome group by point, date ) as t group by point, date order by point
